package com.example.netflixremake.model

import androidx.annotation.DrawableRes

data class Movie(
    val id: Int,
    val coverUrl: String,
    val tittle: String = "",
    val desc: String = "",
    val cast: String = ""
)
