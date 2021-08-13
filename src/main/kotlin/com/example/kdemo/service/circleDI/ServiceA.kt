package com.example.kdemo.service.circleDI

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * 使用 Constructor 模式的循环依赖，启动抛异常。
 * 可改为 Setter 模式。
 */

@Service
class ServiceA {
    @Autowired
    lateinit var serviceB: ServiceB
    
    fun printA() {
        println("AAAA")
    }
}


//@Service
//class ServiceA @Autowired constructor(var serviceB: ServiceB) {
//
//}