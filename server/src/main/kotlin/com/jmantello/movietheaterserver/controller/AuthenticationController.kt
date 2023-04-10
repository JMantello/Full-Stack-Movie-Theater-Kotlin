package com.jmantello.movietheaterserver.controller

import com.jmantello.movietheaterserver.repository.dto.LoginDTO
import com.jmantello.movietheaterserver.repository.dto.RegisterUserDTO
import com.jmantello.movietheaterserver.service.AuthenticationService
import com.jmantello.movietheaterserver.service.UserService
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Key
import java.util.*
import javax.crypto.KeyGenerator


@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("api/auth")
class AuthenticationController(private val userService: UserService, private val authenticationService: AuthenticationService) {

    @PostMapping("register")
    fun registerUser(@RequestBody registerUserDTO: RegisterUserDTO): ResponseEntity<Any> {

        val userExists = userService.emailRegistered(registerUserDTO.email)
        if(userExists)
            return ResponseEntity.badRequest().body("User already registered with that email")

        return ResponseEntity.ok(userService.register(registerUserDTO))
    }

    @PostMapping("login")
    fun login(@RequestBody dto: LoginDTO, response: HttpServletResponse): ResponseEntity<Any> {

        val user = userService.findByEmail(dto.email)
            ?: return ResponseEntity.badRequest().body("Invalid email")

        if(!user.validatePassword(dto.password))
            return ResponseEntity.badRequest().body("Invalid password")

        val cookie = authenticationService.issueCookie(user.id)
        response.addCookie(cookie)

        return ResponseEntity.ok("Successfully logged in")
    }

    @GetMapping("user")
    fun getUser(@CookieValue("jwt") jwt: String?): ResponseEntity<Any> {
        val user = authenticationService.getUser(jwt)
        return ResponseEntity.ok(user)
    }

    @PostMapping("logout")
    fun logout(response: HttpServletResponse): ResponseEntity<Any> {
        // Remove cookie by adding an expired cookie to the responses
        val cookie = Cookie("jwt", "")
        cookie.maxAge = -1
        response.addCookie(cookie)

        return ResponseEntity.ok("Successfully logged out")
    }
}