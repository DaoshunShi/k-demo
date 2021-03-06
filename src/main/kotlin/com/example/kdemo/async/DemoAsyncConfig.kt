package com.example.kdemo.async

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

@Configuration
@EnableAsync
class DemoAsyncConfig : AsyncConfigurer {
    
    
    @Bean("threadPoolTaskExecutor")
    fun threadPoolTaskExecutor(): Executor {
        return ThreadPoolTaskExecutor()
    }
    
    @Bean
    override fun getAsyncExecutor(): Executor? {
        return ThreadPoolTaskExecutor()
    }
    
    override fun getAsyncUncaughtExceptionHandler(): AsyncUncaughtExceptionHandler? {
        return DemoAsyncExceptionHandler()
    }
}