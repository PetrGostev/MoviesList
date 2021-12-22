package ru.petrgostev.movieslist.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import ru.petrgostev.movieslist.databinding.ViewHolderMovieBinding
import ru.petrgostev.movieslist.list.ViewItem.MovieViewItem


class MovieViewsAdapter () : PagingDataAdapter<MovieViewItem, MovieViewHolder>(MovieDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderMovieBinding.inflate(layoutInflater, parent, false)

        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }
}