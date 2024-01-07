package com.example.netflixremake

import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat

class MovieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        var toolbar: Toolbar = findViewById(R.id.movie_toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.arrow_back)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var shadows: LayerDrawable = ContextCompat.getDrawable(this,R.drawable.shadows) as LayerDrawable
        var imageShadow: Drawable = ContextCompat.getDrawable(this,R.drawable.movie_4) as Drawable
        shadows.setDrawableByLayerId(R.id.shadows_img,imageShadow)
        var movieCover = findViewById<ImageView>(R.id.movie_cover)
        movieCover.setImageDrawable(shadows)

    }
}