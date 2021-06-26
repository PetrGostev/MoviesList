package ru.petrgostev.movieslist.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import java.util.concurrent.TimeUnit

class NetworkModule {

    private val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val apiKeyInterceptor = Interceptor { chain ->
        val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter(ORDER_NAME, ORDER_VALUE)
            .addQueryParameter(API_KEY_NAME, API_KEY_VALUE)
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(url)
            .build()

        chain.proceed(newRequest)
    }

    private val httpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(apiKeyInterceptor)
        .addNetworkInterceptor(httpLoggingInterceptor)
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .build()

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    private val contentType = "application/json".toMediaType()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()

    val moviesApi: MoviesApi = retrofit.create()

    companion object {
        private const val BASE_URL = " https://api.nytimes.com/svc/movies/v2/"
        private const val ORDER_NAME = "order"
        private const val ORDER_VALUE = "by-publication-date"
        private const val API_KEY_NAME = "api-key"
        private const val API_KEY_VALUE = "v1hHODCPpKyNkwQTtQtK1QmPPY8GDDdk "
        private const val READ_TIMEOUT: Long = 20
        private const val WRITE_TIMEOUT: Long = 20
        private const val CONNECT_TIMEOUT: Long = 10
    }
}