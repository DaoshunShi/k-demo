package com.example.kdemo.stg

import org.springframework.stereotype.Service

@Service("SRD_WCS")
class SrdAgvTaskService: AgvTaskService() {
    
    override fun doOperation() {
        logger.info("${javaClass.simpleName} log operation SrdAgvTaskService")
    }
}