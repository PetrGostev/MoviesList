package ru.petrgostev.movieslist.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.petrgostev.movieslist.ResultsItem
import ru.petrgostev.movieslist.list.pagging.MoviesPagingSource
import ru.petrgostev.movieslist.network.MoviesApi
import javax.inject.Inject


interface Repository {
    fun getData(): Flow<PagingData<ResultsItem>>
    suspend fun getResponse(offset: Int): List<ResultsItem>
}

class RepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApi,
): Repository {

    override fun getData(): Flow<PagingData<ResultsItem>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { MoviesPagingSource(this) }
        ).flow
    }

    override suspend fun getResponse(offset: Int): List<ResultsItem>{
        return moviesApi.getAll(offset).results
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }
}