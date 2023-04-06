package com.jmantello.movietheaterserver.service

import com.jmantello.movietheaterserver.datasource.MovieDataSource
import com.jmantello.movietheaterserver.model.Genre
import com.jmantello.movietheaterserver.model.Movie
import com.jmantello.movietheaterserver.model.MovieDetails
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class MovieService(@Qualifier("TheMovieDb") private val dataSource: MovieDataSource) {
    fun getTrending(): Collection<Movie> = dataSource.getTrending()
    fun getById(movieId: Int): MovieDetails = dataSource.getById(movieId)
    fun getGenres(): Collection<Genre> = dataSource.getGenres()
}