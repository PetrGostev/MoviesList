package ru.petrgostev.movieslist.utils

import android.content.Context
import android.widget.Toast
import ru.petrgostev.movieslist.R


object ToastUtil {

    fun showToastErrorState(context: Context, error: String) {
        Toast.makeText(
            context,
            context.getString(R.string.error_state, error),
            Toast.LENGTH_LONG
        ).show()
    }
}