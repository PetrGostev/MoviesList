package ru.petrgostev.movieslist.utils

import android.content.Context
import android.widget.Toast
import ru.petrgostev.movieslist.R

object Page{
    var PAGE_SIZE = 20
    var START_OFFSET = 0
}

object ToastUtil {
    fun showToastNoConnectionYet(context: Context) {
        Toast.makeText(
            context,
            context.getString(R.string.no_connection_yet),
            Toast.LENGTH_LONG
        ).show()
    }

    fun showToastErrorState(context: Context, error: String) {
        Toast.makeText(
            context,
            context.getString(R.string.error_state, error),
            Toast.LENGTH_LONG
        ).show()
    }
}