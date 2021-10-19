package com.example.kdemo.stg

import org.springframework.stereotype.Service

@Service("core")
class CoreAgvTaskService : AgvTaskService() {
    
    override fun doOperation() {
        logger.info("${javaClass.simpleName} log operation CoreAgvTaskService")
    }
}