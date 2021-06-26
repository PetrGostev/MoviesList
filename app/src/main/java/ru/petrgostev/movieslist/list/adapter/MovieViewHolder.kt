package ru.petrgostev.movieslist.list.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.petrgostev.movieslist.R
import ru.petrgostev.movieslist.ResultsItem

class MovieViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val poster: ImageView = itemView.findViewById(R.id.poster)
    private val name: TextView = itemView.findViewById(R.id.name)
    private val description: TextView = itemView.findViewById(R.id.description)

    fun onBind(item: ResultsItem) {

        poster.load(item.multimedia?.src)

        name.text = item.displayTitle
        description.text = item.summaryShort
    }
}