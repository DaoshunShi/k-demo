package com.example.kdemo.stg

import com.example.kdemo.ObservableError
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class FactoryForStrategy(val map: Map<String, AgvTaskService> = ConcurrentHashMap()) {

    private val logger=  LoggerFactory.getLogger(javaClass)
    
    fun getStrategy(key : String): AgvTaskService {
        val stg = map[key]
        if (stg == null) {
            logger.warn("no strategy defined")
            throw ObservableError("no strategy defined")
        }
        return stg
    }
    
}