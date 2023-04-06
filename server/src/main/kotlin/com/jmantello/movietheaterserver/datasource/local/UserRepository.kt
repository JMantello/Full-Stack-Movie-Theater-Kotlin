package com.jmantello.movietheaterserver.datasource.local

import com.jmantello.movietheaterserver.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Int> {
}