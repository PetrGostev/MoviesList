package ru.petrgostev.movieslist.list.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.petrgostev.movieslist.databinding.ViewHolderMovieBinding
import ru.petrgostev.movieslist.list.ViewItem.MovieViewItem


class MovieViewHolder (val binding: ViewHolderMovieBinding) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: MovieViewItem) {
        binding.run {
            poster.load(item.src)
            name.text = item.name
            description.text = item.description
        }
    }
}