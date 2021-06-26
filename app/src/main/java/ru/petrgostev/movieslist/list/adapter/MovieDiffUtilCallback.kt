package ru.petrgostev.movieslist.list.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.petrgostev.movieslist.ResultsItem

class MovieDiffUtilCallback : DiffUtil.ItemCallback<ResultsItem>(){
    override fun areItemsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
        return oldItem.summaryShort == newItem.summaryShort
    }

    override fun areContentsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean = oldItem == newItem
}