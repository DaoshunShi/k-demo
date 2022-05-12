package com.example.kdemo.thread

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Instant
import javax.annotation.PostConstruct

@Service
class MultiMethodsSyncTest {
    
    private val logger = LoggerFactory.getLogger(javaClass)
    
    // @PostConstruct
    fun init() {
        logger.info("multi method sync test init")
    
        val ta = Thread {
            while (true) {
                test1("a")
                Thread.sleep(100)
            }
        }
        val tb = Thread {
            while (true) {
                test1("b")
                Thread.sleep(100)
            }
        }
        val te = Thread {
            while (true) {
                test2("e")
                Thread.sleep(100)
            }
        }
        val tf = Thread {
            while (true) {
                test3("f")
                Thread.sleep(100)
            }
        }
    
        ta.start()
        tb.start()
        te.start()
        tf.start()
    }
    
    @Synchronized
    fun test1(s: String) {
        println("${Instant.now()} - test1 - ${Thread.currentThread().name} - $s - start")
        while(true) Thread.sleep(1000)
        println("${Instant.now()} - test1 - ${Thread.currentThread().name} - $s - end")
    }
    
    @Synchronized
    fun test2(s: String) {
        println("${Instant.now()} - test2 - ${Thread.currentThread().name} - $s - start")
        Thread.sleep(1000)
        println("${Instant.now()} - test2 - ${Thread.currentThread().name} - $s - end")
    }
    
    fun test3(s: String) {
        println("${Instant.now()} - test2 - ${Thread.currentThread().name} - $s - start")
        Thread.sleep(1000)
        println("${Instant.now()} - test2 - ${Thread.currentThread().name} - $s - end")
    }
}

fun main() {
    val test = MultiMethodsSyncTest()
    val ta = Thread {
        while (true) {
            test.test1("a")
            Thread.sleep(100)
        }
    }
    val tb = Thread {
        while (true) {
            test.test1("b")
            Thread.sleep(100)
        }
    }
    val tc = Thread {
        while (true) {
            test.test1("c")
            Thread.sleep(100)
        }
    }
    val td = Thread {
        while (true) {
            test.test1("d")
            Thread.sleep(100)
        }
    }
    val te = Thread {
        while (true) {
            test.test2("e")
            Thread.sleep(100)
        }
    }
    val tf = Thread {
        while (true) {
            test.test2("f")
            Thread.sleep(100)
        }
    }
    
    ta.start()
    tb.start()
    te.start()
    tf.start()
}