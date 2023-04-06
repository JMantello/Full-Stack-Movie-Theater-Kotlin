package com.jmantello.movietheaterserver.datasource.network.dto

import com.jmantello.movietheaterserver.model.Movie

class TrendingMoviePage(
    val results: Collection<Movie>
)
