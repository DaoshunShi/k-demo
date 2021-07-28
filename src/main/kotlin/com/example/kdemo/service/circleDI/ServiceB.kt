package com.example.kdemo.service.circleDI

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ServiceB {
    @Autowired
    lateinit var serviceA: ServiceA
}

//@Service
//class ServiceB @Autowired constructor(
//    var serviceA: ServiceA
//) {
//}
