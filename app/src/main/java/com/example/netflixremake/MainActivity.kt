package com.example.netflixremake

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.netflixremake.adapters.MainAdapter
import com.example.netflixremake.model.Category
import com.example.netflixremake.model.Movie
import com.example.netflixremake.util.CategoryTask

class MainActivity : AppCompatActivity(), CategoryTask.CallBack {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var categories = mutableListOf<Category>()

        var rvMain: RecyclerView = findViewById(R.id.main_rv)
        rvMain.layoutManager = LinearLayoutManager(this)
        rvMain.adapter = MainAdapter(categories)

        CategoryTask(this).execute("https://api.tiagoaguiar.co/netflixapp/home?apiKey=54d253d6-bd6c-466b-a34c-053a97ce4613")

    }

    override fun onResult(categories: List<Category>) {
        Log.i("Json Main",categories.toString())
        Toast.makeText(this,"It gave right",Toast.LENGTH_LONG).show()
    }

    override fun onFailure(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
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
