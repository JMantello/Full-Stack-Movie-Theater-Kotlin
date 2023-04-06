package com.jmantello.movietheaterserver.datasource

import com.jmantello.movietheaterserver.model.Genre
import com.jmantello.movietheaterserver.model.Movie
import com.jmantello.movietheaterserver.model.MovieDetails

interface MovieDataSource {

    fun getTrending(): Collection<Movie>
    fun getById(movieId: Int): MovieDetails
    fun getGenres(): Collection<Genre>
}