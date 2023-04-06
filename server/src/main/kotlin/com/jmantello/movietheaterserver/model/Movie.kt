package com.jmantello.movietheaterserver.model

class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String,
    val genre_ids: Collection<Int>
)