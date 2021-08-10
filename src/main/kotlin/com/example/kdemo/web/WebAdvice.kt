package com.example.kdemo.web

import com.example.kdemo.ObservableError
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class WebAdvice {
    
    private val logger = LoggerFactory.getLogger(this::class.java)
    
    @ExceptionHandler(ObservableError::class)
    fun handleObservableError(error: ObservableError): ResponseEntity<Map<String, String>> {
        logger.warn("WebAdvice err msg is ${error.message}")
        val r: MutableMap<String, String> = HashMap(1)
        r["message"] = error.message ?: ""
        return ResponseEntity<Map<String, String>>(r, HttpStatus.BAD_REQUEST)
    }
}