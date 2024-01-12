package com.example.netflixremake

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.netflixremake.model.Category
import com.example.netflixremake.util.CategoryTask

class MainActivity : AppCompatActivity(), CategoryTask.CallBack {

    private lateinit var progress: ProgressBar
    private var categories = mutableListOf<Category>()
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progress = findViewById(R.id.main_progress)
        var rvMain: RecyclerView = findViewById(R.id.main_rv)
        rvMain.layoutManager = LinearLayoutManager(this)

        adapter = MainAdapter(categories){ id ->
            startActivity(
                Intent(this,MovieActivity::class.java)
                    .putExtra("id",id)
            )
        }

        rvMain.adapter = adapter

        CategoryTask(this).execute("https://api.tiagoaguiar.co/netflixapp/home?apiKey=54d253d6-bd6c-466b-a34c-053a97ce4613")

    }

    override fun onPreExecute() {
        progress.visibility = View.VISIBLE
    }

    override fun onResult(categories: List<Category>) {
        progress.visibility = View.GONE
        this.categories.clear()
        this.categories.addAll(categories)
        adapter.notifyDataSetChanged()
    }

    override fun onFailure(message: String) {
        progress.visibility = View.GONE
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
