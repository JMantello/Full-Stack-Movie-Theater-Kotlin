package com.jmantello.movietheaterserver.model

class MovieDetails (
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String,
    val genres: Array<Genre>
)