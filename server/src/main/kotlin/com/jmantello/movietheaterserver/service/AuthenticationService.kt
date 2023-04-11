package com.jmantello.movietheaterserver.service

import com.jmantello.movietheaterserver.model.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.Cookie
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*

//@Service
//class AuthenticationService(
//    private val userService: UserService,
//
//) {
//
//    final val base64EncodedSecretKey =  "wJD53mDEr5zx5Z5HTmZ5D/dC+gFzUIyy/Byyld+Wz2w="
//    // To generate a random secret key
//    //      Base64.getEncoder().encodeToString(
//    //            KeyGenerator.getInstance("HMACSHA256")
//    //            .generateKey().encoded)
//
//    final val keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey)
//    val secretKey: Key = Keys.hmacShaKeyFor(keyBytes)
//
//    fun issueToken(userId: Int): Cookie {
//        val issuer = userId.toString()
//        val jwt = Jwts.builder()
//            .setIssuer(issuer)
//            .setExpiration(Date(System.currentTimeMillis() + 24 * 60 * 1000)) // 24 hours, 1 day
//            .signWith(secretKey)
//            .compact()
//
//        val cookie = Cookie("jwt", jwt)
//        cookie.isHttpOnly = true
//        return cookie
//    }
//
//    fun getIssuerId(jwt: String?): Int? {
//        try { // Parser will throw error if jwt is invalid
//            val body = Jwts.parserBuilder()
//                .setSigningKey(secretKey)
//                .build()
//                .parseClaimsJws(jwt)
//                .body
//
//            val userId = body.issuer.toInt()
//            return userId
//
//        } catch (e: Exception) {
//        }
//
//        return null
//    }
//
//    fun getUser(jwt: String?): User {
//        if(jwt == null)
//            throw Exception("Jwt was null")
//
//        val userId = this.getIssuerId(jwt)
//            ?: throw Exception("Jwt was invalid and could not produce userId")
//
//        val user = userService.findByIdOrNull(userId)
//            ?: throw Exception("User not found")
//
//        return user
//    }
//}