package ru.petrgostev.movieslist.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import ru.petrgostev.movieslist.ResultsItem
import ru.petrgostev.movieslist.list.pagging.MoviesPagingSource
import ru.petrgostev.movieslist.network.NetworkModule
import ru.petrgostev.movieslist.utils.Page

class Repository(private val networkModule: NetworkModule) {

    fun getData(): LiveData<PagingData<ResultsItem>> {
        return Pager(
            config = PagingConfig(pageSize = Page.PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { MoviesPagingSource(this) }
        ).liveData
    }

    suspend fun getResponse(offset: Int): List<ResultsItem>{
        return networkModule.moviesApi.getAll(offset).results
    }
}