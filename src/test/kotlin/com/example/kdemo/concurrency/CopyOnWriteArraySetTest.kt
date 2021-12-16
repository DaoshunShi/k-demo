package com.example.kdemo.concurrency

import java.time.Instant
import java.util.concurrent.CopyOnWriteArraySet
import java.util.concurrent.CountDownLatch

fun main() {
    var map = CopyOnWriteArraySet<Int>()
    val startLatch = CountDownLatch(1)
    
    for (i in 0..9) {
        Thread({
            val threadName = Thread.currentThread().name
            println(Instant.now().toString() + " - " + threadName + " - 开始等待")
            startLatch.await()
            println(Instant.now().toString() + " - " + threadName +  map.add(1))
            println(Instant.now().toString() + " - " + threadName + " - 获得 latch")
        }, "线程$i").start()
    }
    
    Thread.sleep(3000)
    startLatch.countDown()
}