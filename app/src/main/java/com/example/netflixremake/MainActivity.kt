package com.example.netflixremake

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.netflixremake.model.Movie

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rv_main)
        recyclerView.layoutManager = LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)

        val movies = mutableListOf<Movie>()
        for(i in 0 until 20){
            movies.add(Movie(coverImage = R.drawable.movie_4))
        }

        recyclerView.adapter = MainAdapter(movies)

    }

}


    /* Study after
    override fun onStart() {
        super.onStart()
        Log.i("lifeCycles","onStart")
    }

    override fun onResume(){
        super.onResume()
        Log.i("lifeCycles","onResume")
    }

    override fun onPause(){
        super.onPause()
        Log.i("lifeCycles","onPause")
    }

    override fun onStop(){
        super.onStop()
        Log.i("lifeCycles","onStop")
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.i("lifeCycles","onDestroy")
    } */
