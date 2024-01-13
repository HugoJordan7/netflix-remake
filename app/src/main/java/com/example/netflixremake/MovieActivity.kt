package com.example.netflixremake

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.netflixremake.model.Movie
import com.example.netflixremake.model.MovieDetail
import com.example.netflixremake.util.DownloadImageTask
import com.example.netflixremake.util.MovieTask
import java.lang.Exception

class MovieActivity : AppCompatActivity(), MovieTask.Callback, MovieAdapter.Callback {

    private lateinit var progressBar: ProgressBar
    private lateinit var movieCover: ImageView
    private lateinit var movieTitle: TextView
    private lateinit var movieDescription: TextView
    private lateinit var movieCast: TextView
    private var movies = mutableListOf<Movie>()
    private lateinit var adapter: MovieAdapter
    private lateinit var arrayMovies: Array<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        var toolbar: Toolbar = findViewById(R.id.movie_toolbar)
        setSupportActionBar(toolbar)

        progressBar = findViewById(R.id.movie_progress)
        movieCover = findViewById(R.id.movie_cover)
        movieTitle = findViewById(R.id.movie_txt_title)
        movieDescription = findViewById(R.id.movie_description)
        movieCast = findViewById(R.id.movie_cast)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.arrow_back)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = null

        val id = intent?.getIntExtra("id",0) ?: throw Exception("Id not reported!")
        if(id <1) throw Exception("Id not reported!")
        val url = "https://api.tiagoaguiar.co/netflixapp/movie/$id?apiKey=54d253d6-bd6c-466b-a34c-053a97ce4613"

        arrayMovies = intent?.extras?.get("similar") as Array<Movie>


        MovieTask(this).execute(url)


        var layerWithShadow: LayerDrawable = ContextCompat.getDrawable(this,R.drawable.shadows) as LayerDrawable
        var image: Drawable = ContextCompat.getDrawable(this,R.drawable.movie_loading_background) as Drawable
        layerWithShadow.setDrawableByLayerId(R.id.shadows_img,image)
        movieCover.setImageDrawable(layerWithShadow)

        var movieTitle: TextView = findViewById(R.id.movie_txt_title)
        var movieDescription: TextView = findViewById(R.id.movie_description)
        var movieCast: TextView = findViewById(R.id.movie_cast)
        var rvSimilarMovies: RecyclerView = findViewById(R.id.movie_rv_similar)

        movieTitle.text = ""
        movieDescription.text = ""
        movieCast.text = ""

        rvSimilarMovies.layoutManager = GridLayoutManager(this,3)

        //movies.addAll(arrayMovies)
        for (item in arrayMovies){
            if(item.id != id){
                movies.add(item)
            }
        }


        adapter = MovieAdapter(movies,R.layout.movie_item_similar,null,this@MovieActivity)
        rvSimilarMovies.adapter = adapter

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPreExecute() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onResult(movieDetail: MovieDetail) {
        progressBar.visibility = View.GONE
        Log.i("test","movieDetail.toString()")
        var movieRoot: Movie = movieDetail.movie
        movieTitle.text = movieRoot.title
        movieDescription.text = movieRoot.desc
        movieCast.text = getString(R.string.movie_cast,movieRoot.cast)

        DownloadImageTask(
            object : DownloadImageTask.CallBack{
                override fun onResult(bitmap: Bitmap) {
                    var layerWithShadow: LayerDrawable = ContextCompat.getDrawable(this@MovieActivity,R.drawable.shadows) as LayerDrawable
                    var image = BitmapDrawable(resources,bitmap)
                    layerWithShadow.setDrawableByLayerId(R.id.shadows_img,image)
                    movieCover.setImageDrawable(layerWithShadow)
                }
            }
        ).execute(movieRoot.coverUrl)

    }

    override fun onFailure(message: String) {
        progressBar.visibility = View.GONE
    }

    override fun onMovieSimilarClick(id: Int) {
        startActivity(
            Intent(this,MovieActivity::class.java)
                .putExtra("id",id)
                .putExtra("similar",arrayMovies)
        )
        finish()
    }
}