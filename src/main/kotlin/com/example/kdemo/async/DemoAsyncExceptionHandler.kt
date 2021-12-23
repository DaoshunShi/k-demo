package com.example.kdemo.async

import org.slf4j.LoggerFactory
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.stereotype.Service
import java.lang.reflect.Method

@Service
class DemoAsyncExceptionHandler : AsyncUncaughtExceptionHandler {
    
    private val logger = LoggerFactory.getLogger(javaClass)
    
    override fun handleUncaughtException(ex: Throwable, method: Method, vararg params: Any?) {
        logger.info("Exception message - ${ex.message}")
        logger.info("Method name - ${method.name}")
        for (obj in params) {
            logger.info("Parameter value - $obj")
        }
    }
    
}