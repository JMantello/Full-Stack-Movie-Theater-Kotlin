package com.jmantello.movietheaterserver.datasource.network

import com.jmantello.movietheaterserver.datasource.MovieDataSource
import com.jmantello.movietheaterserver.datasource.network.dto.GenreList
import com.jmantello.movietheaterserver.datasource.network.dto.TrendingMoviePage
import com.jmantello.movietheaterserver.model.Genre
import com.jmantello.movietheaterserver.model.Movie
import com.jmantello.movietheaterserver.model.MovieDetails
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import java.io.IOException

@Repository("TheMovieDb")
class NetworkDataSource(
    @Autowired private val restTemplate: RestTemplate
) : MovieDataSource {

    @Value("\${apiUrl}")
    private lateinit var apiUrl: String

    @Value("\${apiKey}")
    private lateinit var apiKey: String

    override fun getTrending(): Collection<Movie> {
        val response: ResponseEntity<TrendingMoviePage> =
            restTemplate.getForEntity(
                "${apiUrl}/trending/movie/week?api_key=${apiKey}")

        return response.body?.results
            ?: throw IOException("Could not fetch trending movies from TMDb")
    }

    override fun getById(movieId: Int): MovieDetails {
        val response: ResponseEntity<MovieDetails> =
            restTemplate.getForEntity(
                "${apiUrl}/movie/${movieId}?api_key=${apiKey}")

        return response.body
            ?: throw IOException("Could not fetch movie from TMDb")
    }

    override fun getGenres(): Collection<Genre> {
        val response: ResponseEntity<GenreList> =
            restTemplate.getForEntity(
                "${apiUrl}/genre/movie/list?api_key=${apiKey}")

        return response.body?.genres
            ?: throw IOException("Could not fetch movie genres from TMDb")
    }
}