package ru.petrgostev.movieslist.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.petrgostev.movieslist.R


class FragmentSplash : Fragment(R.layout.fragment_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startMoviesList()
    }

    private fun startMoviesList() {
        Handler(Looper.getMainLooper()).postDelayed({
            val action =
                FragmentSplashDirections.actionSplashFragmentToListFragment()
            findNavController().navigate(action)
        }, 1000)
    }
}