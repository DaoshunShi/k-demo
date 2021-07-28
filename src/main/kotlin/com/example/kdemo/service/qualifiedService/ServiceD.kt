package com.example.kdemo.service.qualifiedService

import org.springframework.stereotype.Service

@Service("DDD")
class ServiceD : ParentService{

    init {
        println("init d")
    }
}