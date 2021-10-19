package com.example.kdemo.stg

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus

@Controller
@RequestMapping("stg")
class StrategyController(val factoryForStrategy: FactoryForStrategy) {
    
    private val logger = LoggerFactory.getLogger(javaClass)
    
    @PostMapping("operation/{stg}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun operation(@PathVariable stg: String) {
        try {
            val s = factoryForStrategy.getStrategy(stg)
            s.doOperation()
        } catch (e: Throwable) {
            logger.error(e.message, e)
        }
    }
}