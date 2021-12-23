package com.example.kdemo.async

import com.example.kdemo.ObservableError
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.AsyncResult
import org.springframework.stereotype.Service
import java.util.concurrent.Future

@Service
class DemoAsyncService {
    
    private val logger = LoggerFactory.getLogger(javaClass)
    
    @Async
    fun asyncMethodWithVoidReturnType() {
        logger.info("Execute method asynchronously 1. ${Thread.currentThread().name}")
    }
    
    @Async
    fun asyncMethodWithReturnType(): Future<String> {
        logger.info("Execute method asynchronously 2.  ${Thread.currentThread().name}")
        Thread.sleep(5000)
        logger.info("Execute method 2 finished")
        return AsyncResult<String>("Hello async")
    }
    
    @Async("threadPoolTaskExecutor")
    fun asyncMethodWithConfiguredExecutor() {
        logger.info("Execute method asynchronously 3. ${Thread.currentThread().name}")
    }
    
    @Async
    fun asyncMethodWithException() {
        logger.info("Execute method with exception 4. ${Thread.currentThread().name}")
        throw ObservableError("Async 的异常")
    }
}