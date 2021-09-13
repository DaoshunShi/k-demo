package com.example.kdemo

import java.util.concurrent.ConcurrentHashMap

object SynchronizeDemo {
    
    val map: MutableMap<String, String> = ConcurrentHashMap()
    
    init {
        map["1"] = "a"
        map["2"] = "b"
    }
    
    fun tryAcquire1() {
        map["1"]?.let {
            synchronized(it) {
                while (true) {
                    Thread.sleep(100)
                    println("tryAcquire1")
                }
            }
        }
    }
    
    fun tryAcquire2() {
        map["1"]?.let {
            synchronized(it) {
                while (true) {
                    Thread.sleep(100)
                    println("tryAcquire2")
                }
            }
        }
    }
}

fun main() {
    Thread(SynchronizeDemo::tryAcquire1, "线程1").start()
    Thread(SynchronizeDemo::tryAcquire2, "线程2").start()
}