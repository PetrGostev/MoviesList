package ru.petrgostev.movieslist.list

import ru.petrgostev.movieslist.ResultsItem
import ru.petrgostev.movieslist.list.ViewItem.MovieViewItem
import javax.inject.Inject


interface Mapper {
    fun map(resultsItem: ResultsItem) : MovieViewItem
}

class MapperImpl @Inject constructor() : Mapper {
    override fun map(resultsItem: ResultsItem): MovieViewItem =
            MovieViewItem(
                src = resultsItem.multimedia?.src ?: "",
                name = resultsItem.displayTitle,
                description = resultsItem.summaryShort ?: ""
            )
}