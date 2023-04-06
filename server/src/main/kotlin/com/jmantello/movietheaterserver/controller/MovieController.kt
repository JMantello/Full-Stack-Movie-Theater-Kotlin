package com.jmantello.movietheaterserver.controller

import com.jmantello.movietheaterserver.model.Genre
import com.jmantello.movietheaterserver.model.Movie
import com.jmantello.movietheaterserver.model.MovieDetails
import com.jmantello.movietheaterserver.service.MovieService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/movies")
class MovieController(private val service: MovieService) {

    // Exception Handling
    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(e: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    // Endpoints
    @GetMapping("trending")
    fun getTrending(): Collection<Movie> = service.getTrending()

    @GetMapping("/{movieId}")
    fun getMovie(@PathVariable movieId: Int): MovieDetails = service.getById(movieId)

    @GetMapping("/genres")
    fun getGenres(): Collection<Genre> = service.getGenres()

}