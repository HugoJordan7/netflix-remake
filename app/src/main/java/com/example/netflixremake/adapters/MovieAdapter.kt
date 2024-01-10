package com.example.netflixremake.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.netflixremake.R
import com.example.netflixremake.model.Movie
import com.example.netflixremake.util.DownloadImageTask
import com.squareup.picasso.Picasso

class MovieAdapter(
        var list: List<Movie>,
        @LayoutRes var layout: Int
    ): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            val image: ImageView = itemView.findViewById(R.id.movie_jpg)
            // Using the Picasso Library
            //Picasso.get().load(movie.coverUrl).into(image)

            //Using project file manually
            DownloadImageTask(
                object : DownloadImageTask.CallBack{
                    override fun onResult(bitmap: Bitmap) {
                        image.setImageBitmap(bitmap)
                    }
                }
            ).execute(movie.coverUrl)
        }
    }

}