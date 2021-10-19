package com.example.kdemo.stg

import org.slf4j.LoggerFactory

abstract class AgvTaskService {

    val logger = LoggerFactory.getLogger(javaClass)
    
    open fun doOperation() {
        logger.info("${javaClass.simpleName} log operation AgvTaskService")
    }
}