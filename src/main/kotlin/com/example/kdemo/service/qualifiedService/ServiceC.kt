package com.example.kdemo.service.qualifiedService

import org.springframework.stereotype.Service

@Service("ccc")
class ServiceC : ParentService {

    init {
        println("init ccc")
    }
}