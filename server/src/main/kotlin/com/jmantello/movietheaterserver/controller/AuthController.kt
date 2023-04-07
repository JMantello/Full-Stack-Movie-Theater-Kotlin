package com.jmantello.movietheaterserver.controller

import com.jmantello.movietheaterserver.model.User
import com.jmantello.movietheaterserver.repository.dto.LoginDTO
import com.jmantello.movietheaterserver.repository.dto.RegisterUserDTO
import com.jmantello.movietheaterserver.service.UserService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Key
import java.util.*
import javax.crypto.KeyGenerator


@RestController
@RequestMapping("api/auth")
class AuthController(private val userService: UserService) {

    @PostMapping("register")
    fun registerUser(@RequestBody user: RegisterUserDTO): ResponseEntity<User> =
        userService.register(user)

    @PostMapping("login")
    fun login(@RequestBody dto: LoginDTO, response: HttpServletResponse): ResponseEntity<Any> {
        val user = userService.findByEmail(dto.email)
            ?: return ResponseEntity.badRequest().body("Invalid email")

        if(!user.validatePassword(dto.password))
            return ResponseEntity.badRequest().body("Invalid password")

        // Creates random secret key to sign jwt with
        val base64EncodedSecretKey =
            Base64.getEncoder()
            .encodeToString(KeyGenerator
                .getInstance("HMACSHA256")
                .generateKey().encoded)

        val keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey)
        val key: Key = Keys.hmacShaKeyFor(keyBytes)

        val issuer = user.id.toString()
        val jwt = Jwts.builder()
            .setIssuer(issuer)
            .setExpiration(Date(System.currentTimeMillis() + 24 * 60 * 1000)) // 24 hours, 1 day
            .signWith(key)
            .compact()

        var cookie = Cookie("jwt", jwt)
        cookie.isHttpOnly = true
        response.addCookie(cookie)

        return ResponseEntity.ok("Success")
    }
}