package com.example.kdemo.controller

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
@RequestMapping("/api/inv/detail")
class InvDetailController {
    
    private val logger = LoggerFactory.getLogger(javaClass)
    
    @PostMapping("/ping")
    fun ping(): Instant {
        return Instant.now()
    }
    
}