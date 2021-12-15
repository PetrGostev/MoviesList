package ru.petrgostev.movieslist.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.petrgostev.movieslist.Response


interface MoviesApi {

    @GET("reviews/all.json")
    suspend fun getAll(@Query("offset") offset: Int): Response
}