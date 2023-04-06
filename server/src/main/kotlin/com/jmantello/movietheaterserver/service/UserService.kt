package com.jmantello.movietheaterserver.service

import com.jmantello.movietheaterserver.model.User
import com.jmantello.movietheaterserver.datasource.local.UserRepository
import com.jmantello.movietheaterserver.datasource.local.dto.RegisterUserDTO
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class UserService(private val userService: UserRepository) {

    fun register(dto: RegisterUserDTO): ResponseEntity<User> {
        val user = User()
        user.name = dto.name
        user.email = dto.email
        user.password = dto.password
        
        return ResponseEntity.ok(this.userService.save(user))
    }
}