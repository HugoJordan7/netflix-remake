package com.example.netflixremake

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.netflixremake.adapters.MainAdapter
import com.example.netflixremake.model.Category
import com.example.netflixremake.model.Movie
import com.example.netflixremake.util.CategoryTask

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var categories = mutableListOf<Category>()

        var rvMain: RecyclerView = findViewById(R.id.main_rv)
        rvMain.layoutManager = LinearLayoutManager(this)
        rvMain.adapter = MainAdapter(categories)

        CategoryTask().execute("https://api.thiagoaguiar.co/netflixapp/home?apiKey=54d253d6-bd6c-466b-a34c-053a97ce4613")

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
