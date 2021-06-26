package ru.petrgostev.movieslist.list

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import ru.petrgostev.movieslist.ResultsItem
import ru.petrgostev.movieslist.repository.Repository

class MoviesListViewModel(private val repository: Repository) : ViewModel() {

    private val _mutableIsConnected = MutableLiveData<Boolean>(true)
    val _mutablePagingList = MutableLiveData<PagingData<ResultsItem>>()

    val isConnected: LiveData<Boolean> get() = _mutableIsConnected
    val moviesPagingList: LiveData<PagingData<ResultsItem>> get() = _mutablePagingList

    private var result: LiveData<PagingData<ResultsItem>>? = null

    private val pagingObserver = Observer<PagingData<ResultsItem>> {
        _mutablePagingList.postValue(it)
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(
            ContentValues.TAG,
            "Coroutine exception, scope active:${viewModelScope.isActive}",
            throwable
        )
        viewModelScope.launch {
            _mutableIsConnected.postValue(false)
        }
    }

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch(exceptionHandler) {
            loadData()
        }
    }

    private  fun loadData() {
        result = repository.getData().cachedIn(viewModelScope)
        result?.observeForever(pagingObserver)
    }

    override fun onCleared() {
        result?.removeObserver(pagingObserver)
        super.onCleared()
    }
}