package com.jmantello.movietheaterserver.repository

import com.jmantello.movietheaterserver.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Int> {
    fun findByEmail(email: String): User?
}