package com.example.kdemo.service

import com.example.kdemo.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepo: UserRepository) {
    
    @Bean
    fun test() {
    }
    
    
    fun login(loginName: String, password: String): Boolean {
        val user = userRepo.findByLoginName(loginName)
        return user.password == password
    }
    
}