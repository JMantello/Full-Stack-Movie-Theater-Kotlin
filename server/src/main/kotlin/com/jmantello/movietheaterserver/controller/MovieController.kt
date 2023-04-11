package com.jmantello.movietheaterserver.controller

import com.jmantello.movietheaterserver.model.Genre
import com.jmantello.movietheaterserver.model.Movie
import com.jmantello.movietheaterserver.model.MovieDetails
import com.jmantello.movietheaterserver.service.MovieService
import com.jmantello.movietheaterserver.service.TokenService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/movies")
class MovieController(private val movieService: MovieService, private val tokenService: TokenService) {

    // Exception Handling
    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(e: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    // Endpoints
    @GetMapping("trending")
    fun getTrending(): Collection<Movie> = movieService.getTrending()

    @GetMapping("{movieId}")
    fun getMovie(@PathVariable movieId: Int): MovieDetails = movieService.getById(movieId)

    @GetMapping("genres")
    fun getGenres(): Collection<Genre> = movieService.getGenres()

    @GetMapping("watch/{movieId}")
    fun watch(@CookieValue("jwt") jwt: String?, @PathVariable movieId: Int): ResponseEntity<Any> {

        jwt ?: return ResponseEntity.badRequest().body("Unauthorized: Jwt string was null")
        val user = tokenService.parseToken(jwt)
        val movie = movieService.getById(movieId)
        user.watchHistory.add(movie.id)
        return ResponseEntity.ok(movie)
    }

    @GetMapping("watchHistory")
    fun getWatchHistory(@CookieValue("jwt") jwt: String?): ResponseEntity<Any> {

        jwt ?: return ResponseEntity.badRequest().body("Unauthorized: Jwt string was null")
        val user = tokenService.parseToken(jwt)

        val watchHistory = mutableListOf<MovieDetails>()
        for (movieId:Int in user.watchHistory)
            watchHistory.add(movieService.getById(movieId))

        return ResponseEntity.ok(watchHistory)
    }

}