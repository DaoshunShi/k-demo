package com.example.kdemo.schedule

import org.slf4j.LoggerFactory
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

class SerialScheduledExecutorService(threadsNum: Int) {
    
    private val logger = LoggerFactory.getLogger(javaClass)
    
    private val executing: MutableMap<String, Boolean> = Collections.synchronizedMap(HashMap())
    
    private val scheduledExecutor: ScheduledExecutorService = Executors.newScheduledThreadPool(threadsNum)
    
    fun scheduleAtFixedRateSerially(
        id: String, command: Runnable, initialDelay: Long, period: Long, unit: TimeUnit
    ): ScheduledFuture<*> {
        return scheduledExecutor.scheduleAtFixedRate(object : Runnable {
            override fun run() {
                try {
                    synchronized(executing) {
                        if (executing[id] == true) return
                        executing[id] = true
                    }
                    try {
                        command.run()
                    } finally {
                        synchronized(executing) {
                            executing[id] = false
                        }
                    }
                } catch (e: Throwable) {
                    logger.error("定时指定:{}", id)
                }
            }
        }, initialDelay, period, unit)
    }
    
    fun scheduleAtFixedRate(command: Runnable, initialDelay: Long, period: Long, unit: TimeUnit): ScheduledFuture<*> {
        return scheduledExecutor.scheduleAtFixedRate(command, initialDelay, period, unit)
    }
    
    fun shutdown() {
        scheduledExecutor.shutdown()
    }
    
}