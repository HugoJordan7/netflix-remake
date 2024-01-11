package com.example.netflixremake

import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.netflixremake.adapters.MovieAdapter
import com.example.netflixremake.model.Movie

class MovieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        var toolbar: Toolbar = findViewById(R.id.movie_toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.arrow_back)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null

        var shadows: LayerDrawable = ContextCompat.getDrawable(this,R.drawable.shadows) as LayerDrawable
        var imageShadow: Drawable = ContextCompat.getDrawable(this,R.drawable.movie_4) as Drawable
        shadows.setDrawableByLayerId(R.id.shadows_img,imageShadow)
        var movieCover = findViewById<ImageView>(R.id.movie_cover)
        movieCover.setImageDrawable(shadows)

        var movieTitle: TextView = findViewById(R.id.movie_txt_title)
        var movieDescription: TextView = findViewById(R.id.movie_description)
        var movieCast: TextView = findViewById(R.id.movie_cast)
        var rvSimilarMovies: RecyclerView = findViewById(R.id.movie_rv_similar)

        movieTitle.text = "Batman"
        movieDescription.text = "Imagine que essa é uma descrição do batman: AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
        movieCast.text = getString(R.string.movie_cast,"Actor 1, Actor 2, Actress 1 and Actress 2")

        val movies = mutableListOf<Movie>()

        rvSimilarMovies.layoutManager = GridLayoutManager(this,3)
        rvSimilarMovies.adapter = MovieAdapter(movies,R.layout.movie_item_similar)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}