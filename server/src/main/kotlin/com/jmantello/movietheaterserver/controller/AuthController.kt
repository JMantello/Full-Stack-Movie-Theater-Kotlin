package com.jmantello.movietheaterserver.controller

import com.jmantello.movietheaterserver.datasource.local.dto.RegisterUserDTO
import com.jmantello.movietheaterserver.model.User
import com.jmantello.movietheaterserver.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/auth")
class AuthController(private val userService: UserService) {

    @PostMapping("register")
    fun registerUser(@RequestBody user: RegisterUserDTO): ResponseEntity<User> =
        userService.register(user)
}