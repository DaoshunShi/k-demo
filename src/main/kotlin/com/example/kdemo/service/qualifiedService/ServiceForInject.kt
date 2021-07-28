package com.example.kdemo.service.qualifiedService

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class ServiceForInject {
    @Autowired()
    @Qualifier("DDD")
    lateinit var serviceC: ParentService
}