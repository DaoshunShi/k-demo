package com.example.kdemo

import org.springframework.boot.Banner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class KDemoApplication

fun main(args: Array<String>) {
    // runApplication<KDemoApplication>(*args)
    val app = SpringApplication(KDemoApplication::class.java)
    app.setBannerMode(Banner.Mode.OFF)
    app.run(*args)
}
