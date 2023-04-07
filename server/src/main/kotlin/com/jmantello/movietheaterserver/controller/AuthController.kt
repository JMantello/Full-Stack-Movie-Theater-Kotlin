package com.jmantello.movietheaterserver.controller

import com.jmantello.movietheaterserver.repository.dto.LoginDTO
import com.jmantello.movietheaterserver.repository.dto.RegisterUserDTO
import com.jmantello.movietheaterserver.service.UserService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
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

    // Creates random secret key to sign jwt with
    val base64EncodedSecretKey =
        Base64.getEncoder()
            .encodeToString(KeyGenerator
                .getInstance("HMACSHA256")
                .generateKey().encoded)

    val keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey)
    val secretKey: Key = Keys.hmacShaKeyFor(keyBytes)

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

        val issuer = user.id.toString()
        val jwt = Jwts.builder()
            .setIssuer(issuer)
            .setExpiration(Date(System.currentTimeMillis() + 24 * 60 * 1000)) // 24 hours, 1 day
            .signWith(secretKey)
            .compact()

        var cookie = Cookie("jwt", jwt)
        cookie.isHttpOnly = true
        response.addCookie(cookie)

        return ResponseEntity.ok("Successfully logged in")
    }

    @GetMapping("user")
    fun getUser(@CookieValue("jwt") jwt: String?): ResponseEntity<Any> {

        if(jwt == null)
            return ResponseEntity.status(401).body("Unauthenticated, jwt was null")

        try {
            // If jwt invalid, will throw error, hence the try catch
            val body = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwt)
                .body

            val userId = body.issuer.toInt()
            val user = userService.findByIdOrNull(userId)

            return ResponseEntity.ok(user)

        } catch (e: Exception) {
            return ResponseEntity.status(401).body("Unauthenticated, jwt was invalid")
        }
    }

    @PostMapping("logout")
    fun logout(response: HttpServletResponse): ResponseEntity<Any> {
        // Remove cookie by adding an expired cookie
        var cookie = Cookie("jwt", "")
        cookie.maxAge = -1
        response.addCookie(cookie)

        return ResponseEntity.ok("Successfully logged out")
    }
}