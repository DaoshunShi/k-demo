package com.example.kdemo

import com.example.kdemo.config.DemoConfig
import com.example.kdemo.helper.JsonHelper
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.Banner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary

@SpringBootApplication
@EnableConfigurationProperties(DemoConfig::class)
class KDemoApplication {
    
    @Bean
    @Primary
    fun objectMapper(): ObjectMapper {
        return JsonHelper.mapper
    }
    
}

fun main(args: Array<String>) {
    // runApplication<KDemoApplication>(*args)
    val app = SpringApplication(KDemoApplication::class.java)
    app.setBannerMode(Banner.Mode.OFF)
    app.run(*args)
}
