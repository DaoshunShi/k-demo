package com.example.kdemo.helper

import org.slf4j.LoggerFactory
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

object ThreadHelper {
    
    private val logger = LoggerFactory.getLogger(javaClass)
    
    val bgExecutor = Executors.newFixedThreadPool(20)
    
    fun buildNamedThreadFactory(name: String): ThreadFactory {
        val counter = AtomicInteger(0)
        return ThreadFactory { r ->
            val no = counter.incrementAndGet()
            val thread = Thread(r, "$name-$no")
            thread.setUncaughtExceptionHandler { t, e ->
                logger.error("uncaught exception ${t.id}", e)
            }
            thread
        }
    }
    
}