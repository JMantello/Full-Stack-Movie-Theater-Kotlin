package com.jmantello.movietheaterserver.controller

import com.jmantello.movietheaterserver.repository.dto.LoginDTO
import com.jmantello.movietheaterserver.repository.dto.RegisterUserDTO
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

    @PostMapping("login")
    fun login(@RequestBody dto: LoginDTO): ResponseEntity<Any> {
        val user = userService.findByEmail(dto.email)
            ?: return ResponseEntity.badRequest().body("Invalid email")

        if(!user.validatePassword(dto.password))
            return ResponseEntity.badRequest().body("Invalid password")

        return ResponseEntity.ok(user)
    }
}