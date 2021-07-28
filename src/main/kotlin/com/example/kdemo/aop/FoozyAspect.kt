package com.example.kdemo.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.*
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.Instant

@Component
@Aspect
class FoozyAspect {

    private val logger = LoggerFactory.getLogger(FoozyAspect::class.java)

    @Before("@annotation(com.example.kdemo.aop.Foozy)")
    fun doStuff() {
        logger.info("@Before do stuff")
    }

    @Around("@annotation(com.example.kdemo.aop.Foozy)")
    fun statCost(pjp: ProceedingJoinPoint) {
        val start = Instant.now()
        logger.info("@Around before proceed")
        pjp.proceed()
        logger.info("@Around after proceed")
        logger.info("@Around cost is ${Instant.now().minusMillis(start.toEpochMilli()).toEpochMilli()}")
    }

    @After("@annotation(com.example.kdemo.aop.Foozy)")
    fun doStuff2(){
        logger.info("@After do stuff 2")
    }

    @AfterReturning("@annotation(com.example.kdemo.aop.Foozy)")
    fun doStuff3() {
        logger.info("@AfterReturning do stuff 3")
    }

    @AfterThrowing("@annotation(com.example.kdemo.aop.Foozy)")
    fun doStuff4() {
        logger.info("@AfterThrowing do stuff 4")
    }


}