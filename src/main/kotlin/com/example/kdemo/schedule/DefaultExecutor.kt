package com.example.kdemo.schedule

import org.slf4j.LoggerFactory
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

object DefaultExecutor {
    
    private val logger = LoggerFactory.getLogger(javaClass)
    
    val bgFixedExecutor: ExecutorService = Executors.newFixedThreadPool(10)
    val bgCacheExecutor: ExecutorService = Executors.newCachedThreadPool()
    val bgScheduledExecutor: ScheduledExecutorService = Executors.newScheduledThreadPool(6)
    
    val serialScheduledExecutor = SerialScheduledExecutorService(6)
    
    fun dispose() {
        logger.info("销毁默认的线程执行器...")
        
        bgFixedExecutor.shutdownNow()
        bgCacheExecutor.shutdownNow()
        bgScheduledExecutor.shutdownNow()
        
        serialScheduledExecutor.shutdown()
        
        logger.info("销毁默认的线程执行器 - 完成")
    }
    
}

