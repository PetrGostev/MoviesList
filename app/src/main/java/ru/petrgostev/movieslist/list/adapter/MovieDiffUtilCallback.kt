package ru.petrgostev.movieslist.list.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.petrgostev.movieslist.list.ViewItem.MovieViewItem


class MovieDiffUtilCallback : DiffUtil.ItemCallback<MovieViewItem>(){
    override fun areItemsTheSame(oldItem: MovieViewItem, newItem: MovieViewItem): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: MovieViewItem, newItem: MovieViewItem): Boolean = oldItem == newItem
}