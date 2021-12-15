package ru.petrgostev.movieslist.list

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.petrgostev.movieslist.R
import ru.petrgostev.movieslist.databinding.FragmentListBinding
import ru.petrgostev.movieslist.list.adapter.MovieViewsAdapter
import ru.petrgostev.movieslist.list.pagging.MovieLoadStateAdapter
import ru.petrgostev.movieslist.utils.ToastUtil


@AndroidEntryPoint
class MovieListFragment : Fragment(R.layout.fragment_list) {

    private val model: MoviesListViewModel by viewModels()

    private var binding: FragmentListBinding? = null
    private lateinit var adapter: MovieViewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListBinding.bind(view)

        initAdapter()
        configureMoviesSwipe()
        collectState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initAdapter() {
        with(binding ?: return) {
            adapter = MovieViewsAdapter()
            recycler.adapter = adapter.withLoadStateHeaderAndFooter(
                header = MovieLoadStateAdapter { adapter.retry() },
                footer = MovieLoadStateAdapter { adapter.retry() }
            )
            adapter.addLoadStateListener { loadState ->
                recycler.isVisible =
                    loadState.source.refresh is LoadState.NotLoading
                loader.isVisible = loadState.source.refresh is LoadState.Loading
                swipe.isRefreshing = false
                retryButton.isVisible = loadState.source.refresh is LoadState.Error

                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    ToastUtil.showToastErrorState(requireContext(), it.toString())
                }
            }
        }
    }

    private fun configureMoviesSwipe() {
        with(binding ?: return) {
            swipe.setOnRefreshListener {
                adapter.refresh()
            }
        }
    }

    private fun collectState() {
        viewLifecycleOwner.lifecycleScope.launch {
            model.getMovies().collectLatest { movies ->
                adapter.submitData(movies)
            }
        }
    }
}