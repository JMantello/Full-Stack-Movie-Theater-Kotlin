package com.jmantello.movietheaterserver.service

import com.jmantello.movietheaterserver.repository.UserRepository
import com.jmantello.movietheaterserver.model.User
import com.jmantello.movietheaterserver.repository.dto.RegisterUserDTO
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun register(dto: RegisterUserDTO): ResponseEntity<User> {
        val user = User()
        user.email = dto.email
        // Check if email unique
        user.name = dto.name
        user.password = dto.password
        return ResponseEntity.ok(userRepository.save(user))
    }

    fun findByEmail(email: String): User? = userRepository.findByEmail(email)
}