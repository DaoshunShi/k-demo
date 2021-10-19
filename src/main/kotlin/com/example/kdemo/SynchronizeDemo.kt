package com.example.kdemo

import java.lang.Thread.sleep
import java.util.concurrent.ConcurrentHashMap

object SynchronizeDemo {
    
    private val map: MutableMap<String, String> = ConcurrentHashMap()
    
    init {
        map["1"] = "a"
        map["2"] = "b"
    }
    
    fun tryAcquire1() {
        tryAcquire("1")
    }
    
    fun tryAcquire2() {
        tryAcquire("1")
    }
    
    fun tryAcquire(key: String) {
        map[key]?.let {
            var flag = true
            var i = 1
            while (flag) {
                synchronized(it) {
                    Thread.sleep(100)
                    println("${Thread.currentThread().name} tryAcquire $key")
                    i++
                    if (i > 100) flag = false
                }
            }
        }
    }
}

object SynchronizeDemo2 {
    var map: MutableMap<String, String> = ConcurrentHashMap<String, String>()
    
    init {
        map["1"] = "a20"
        map["2"] = "b100"
    }
    
    private fun tryAcquire(key: String, value: String) {
        var flag = true
        while (flag) {
            map[key]?.let {
                synchronized(it) {
                    println("try acquire $key $value")
                    if (it.isBlank() || it == value) {
                        map[key] = value
                        flag = false
                        println("acquired $key $value")
                    } else {
                        sleep(200)
                    }
                }
            }
        }
    }
    
    private fun tryRelease(key: String, value: String) {
        var flag = true
        var i = 1
        while (flag) {
            map[key]?.let {
                synchronized(it) {
                    println("try release $key $value$i")
                    if (it == value + i) {
                        map[key] = ""
                        flag = false
                        println("release $key $value$i")
                    } else {
                        sleep(200)
                    }
                    i++
                }
            }
        }
    }
    
    fun tryRelease1() {
        tryRelease("1", "a")
    }
    
    fun tryAcquire1() {
        tryAcquire("1", "a200")
    }
    
}

fun main() {
    // Thread(SynchronizeDemo::tryAcquire1, "线程1").start()
    // Thread(SynchronizeDemo::tryAcquire2, "线程2").start()
    
    Thread(SynchronizeDemo2::tryRelease1, "释放线程1").start()
    Thread(SynchronizeDemo2::tryAcquire1, "获取线程1").start()
}