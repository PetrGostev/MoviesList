package ru.petrgostev.movieslist.list.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.petrgostev.movieslist.R
import ru.petrgostev.movieslist.list.ViewItem.MovieViewItem


class MovieViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val poster: ImageView = itemView.findViewById(R.id.poster)
    private val name: TextView = itemView.findViewById(R.id.name)
    private val description: TextView = itemView.findViewById(R.id.description)

    fun onBind(item: MovieViewItem) {

        poster.load(item.src)

        name.text = item.name
        description.text = item.description
    }
}