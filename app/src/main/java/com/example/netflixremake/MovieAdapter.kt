package com.example.netflixremake

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.netflixremake.model.Movie
import com.example.netflixremake.util.DownloadImageTask

class MovieAdapter(
    var listMovie: List<Movie>,
    @LayoutRes var layout: Int,
    var context: ((Int, List<Movie>?) -> Unit)? = null,
    var callback: Callback? = null
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    interface Callback {
        fun onMovieSimilarClick(id: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(listMovie[position])
    }

    override fun getItemCount(): Int {
        return listMovie.size
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            val image: ImageView = itemView.findViewById(R.id.movie_jpg)
            image.setOnClickListener {
                context?.invoke(movie.id, listMovie)
                callback?.onMovieSimilarClick(movie.id)

            }

            // Using the Picasso Library
            //Picasso.get().load(movie.coverUrl).into(image)

            /*Using project file manually*/
            DownloadImageTask(
                object : DownloadImageTask.CallBack {
                    override fun onResult(bitmap: Bitmap) {
                        image.setImageBitmap(bitmap)
                    }
                }
            ).execute(movie.coverUrl)
        }
    }

}