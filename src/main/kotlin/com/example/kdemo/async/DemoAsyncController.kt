package com.example.kdemo.async

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/async")
class DemoAsyncController(
    private val demoAsyncService: DemoAsyncService
) {
    
    private val logger = LoggerFactory.getLogger(javaClass)
    
    @PostMapping("async-with-void")
    fun asyncWithVoidReturnType() {
        logger.info("Receive async-with-void request.")
        demoAsyncService.asyncMethodWithVoidReturnType()
    }
    
    @PostMapping("async-with-return")
    fun asyncWithReturnType() {
        logger.info("Receive async-with-return request.")
        val future = demoAsyncService.asyncMethodWithReturnType()
        while (true) {
            if (future.isDone) {
                logger.info("Result from asynchronous process - ${future.get()}")
                break
            }
            
            logger.info("Continue doing something else.")
            Thread.sleep(1000)
        }
    }
    
    @PostMapping("async-with-configured-executor")
    fun asyncWithConfiguredExecutor() {
        logger.info("Receive async-with-configured-executor.")
        demoAsyncService.asyncMethodWithConfiguredExecutor()
    }
}