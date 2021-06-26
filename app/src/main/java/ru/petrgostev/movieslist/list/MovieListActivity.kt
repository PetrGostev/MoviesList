package ru.petrgostev.movieslist.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.petrgostev.movieslist.ResultsItem
import ru.petrgostev.movieslist.databinding.ActivityListBinding
import ru.petrgostev.movieslist.list.adapter.MovieViewsAdapter
import ru.petrgostev.movieslist.list.pagging.MovieLoadStateAdapter
import ru.petrgostev.movieslist.network.NetworkModule
import ru.petrgostev.movieslist.repository.Repository
import ru.petrgostev.movieslist.utils.ToastUtil

class MovieListActivity : AppCompatActivity() {

    private val model: MoviesListViewModel by viewModels {
        MoviesListViewModelFactory(Repository(NetworkModule()))
    }

    private lateinit var binding: ActivityListBinding
    private lateinit var adapter: MovieViewsAdapter

    private var moviesJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        model.isConnected.observe(this, this::showToastNoConnectionYet)
        model.moviesPagingList.observe(this, {
            this.updateAdapter(it)
        })

        initAdapter()
        configureMoviesSwipe()
    }

    private fun initAdapter() {
        adapter = MovieViewsAdapter()
        binding.recycler.adapter = adapter.withLoadStateHeaderAndFooter(
            header = MovieLoadStateAdapter { adapter.retry() },
            footer = MovieLoadStateAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener { loadState ->
            binding.recycler.isVisible =
                loadState.source.refresh is LoadState.NotLoading
            binding.loader.isVisible = loadState.source.refresh is LoadState.Loading
            binding.swipe.isRefreshing = false
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                ToastUtil.showToastErrorState(this, it.toString())
            }
        }
    }

    private fun configureMoviesSwipe() {
        binding.swipe.setDistanceToTriggerSync(DISTANCE_TRIGGER)

        binding.swipe.setOnRefreshListener {
            model.getData()
        }
    }

    private fun updateAdapter(results: PagingData<ResultsItem>) {
        moviesJob?.cancel()
        moviesJob = lifecycleScope.launch {
            adapter.submitData(results)
        }
    }

    private fun showToastNoConnectionYet(isConnect: Boolean) {
        if (!isConnect) {
            binding.swipe.isRefreshing = false
            ToastUtil.showToastNoConnectionYet(this)
        }
    }

    companion object {
        private const val DISTANCE_TRIGGER = 10
    }
}