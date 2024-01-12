package com.example.netflixremake

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.netflixremake.model.Category

class MainAdapter(
        var list: List<Category>,
        var context: ( (Int) -> Unit )? = null
    ): RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.category_item,parent,false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MainViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(category: Category){
            var rvCategory: RecyclerView = itemView.findViewById(R.id.category_rv)
            rvCategory.layoutManager = LinearLayoutManager(itemView.context,RecyclerView.HORIZONTAL,false)
            rvCategory.adapter = MovieAdapter(category.movies, R.layout.movie_item,context)

            var categoryTitle: TextView = itemView.findViewById(R.id.category_name)
            categoryTitle.text = category.name
        }
    }

}