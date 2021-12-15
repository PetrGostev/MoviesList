package ru.petrgostev.movieslist.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.petrgostev.movieslist.list.Mapper
import ru.petrgostev.movieslist.list.MapperImpl
import ru.petrgostev.movieslist.repository.Repository
import ru.petrgostev.movieslist.repository.RepositoryImpl


@Module
@InstallIn(SingletonComponent::class)

interface MoviesModule {

    @Binds
    fun bindMoviesRepository(
        impl: RepositoryImpl,
    ): Repository

    @Binds
    fun bindMoviesMapper(
        impl: MapperImpl,
    ): Mapper
}