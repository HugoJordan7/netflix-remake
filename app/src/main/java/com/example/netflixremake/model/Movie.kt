package com.example.netflixremake.model

import java.io.Serializable

data class Movie(
    val id: Int,
    val coverUrl: String,
    val title: String? = "",
    val desc: String = "",
    val cast: String = ""
): Serializable