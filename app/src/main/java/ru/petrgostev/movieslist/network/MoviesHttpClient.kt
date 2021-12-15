package ru.petrgostev.movieslist.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.petrgostev.movieslist.BuildConfig
import java.util.concurrent.TimeUnit
import javax.inject.Inject


interface MoviesHttpClient {
    val moviesApi: MoviesApi
}

class MoviesHttpClientImpl @Inject constructor() : MoviesHttpClient{

    private val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        if (BuildConfig.DEBUG) level = HttpLoggingInterceptor.Level.BODY else level =
            HttpLoggingInterceptor.Level.NONE
    }

    private val apiKeyInterceptor = Interceptor { chain ->
        val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter(ORDER_NAME, BuildConfig.ORDER_VALUE)
            .addQueryParameter(API_KEY_NAME, BuildConfig.THE_MOVIE_DB_API_KEY)
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

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override val moviesApi: MoviesApi by lazy(LazyThreadSafetyMode.NONE) { retrofit.create(MoviesApi::class.java) }

    companion object {
        private const val ORDER_NAME = "order"
        private const val API_KEY_NAME = "api-key"
        private const val READ_TIMEOUT: Long = 20
        private const val WRITE_TIMEOUT: Long = 20
        private const val CONNECT_TIMEOUT: Long = 10
    }
}