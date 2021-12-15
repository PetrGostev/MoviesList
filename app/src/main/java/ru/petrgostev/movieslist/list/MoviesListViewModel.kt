package ru.petrgostev.movieslist.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.petrgostev.movieslist.list.ViewItem.MovieViewItem
import ru.petrgostev.movieslist.repository.Repository
import javax.inject.Inject


@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val repository: Repository,
    private val mapper: Mapper
) : ViewModel() {

    fun getMovies(): Flow<PagingData<MovieViewItem>> {
        return repository.getData()
            .map { result ->
                result.map {
                    mapper.map(it)
                }
            }
            .cachedIn(viewModelScope)
    }
}