package ru.petrgostev.movieslist.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.petrgostev.movieslist.network.MoviesApi
import ru.petrgostev.movieslist.network.MoviesHttpClient
import ru.petrgostev.movieslist.network.MoviesHttpClientImpl
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {

    @Binds
    @Singleton
    fun bindMovieHttpClient(
        impl: MoviesHttpClientImpl,
    ): MoviesHttpClient

    @Module
    @InstallIn(SingletonComponent::class)
    object ApiWrapperModule {

        @Provides
        @Singleton
        fun provideMoviesApi(client: MoviesHttpClient): MoviesApi = client.moviesApi
    }
}