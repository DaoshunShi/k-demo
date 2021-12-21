package com.example.kdemo.repository

import com.example.kdemo.entity.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Int> {
    
    fun findByLoginName(loginName: String): User
}