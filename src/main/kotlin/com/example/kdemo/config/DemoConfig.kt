package com.example.kdemo.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "demo")
class DemoConfig {
    var lang: String = ""
    
    var mongo: String = ""
}