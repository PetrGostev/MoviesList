package ru.petrgostev.movieslist.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import ru.petrgostev.movieslist.R
import ru.petrgostev.movieslist.list.MovieListActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startMoviesListActivity()
    }

    private fun startMoviesListActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MovieListActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)
    }
}