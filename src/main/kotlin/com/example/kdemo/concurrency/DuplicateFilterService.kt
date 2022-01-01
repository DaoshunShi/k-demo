package com.example.kdemo.concurrency

import com.example.kdemo.helper.IdHelper
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors

@Service
class DuplicateFilterService {
    private val logger = LoggerFactory.getLogger(javaClass)
    
    private val reqMap: MutableMap<String, String> = ConcurrentHashMap<String, String>()
    
    // 自旋锁
    fun lock(k: String, v: String) {
        var flag = false
        while (true) {
            synchronized(this) {
                if (!reqMap.containsKey(k)) {
                    reqMap[k] = v
                    flag = true
                    logger.debug("$v get lock $k")
                } else if (reqMap[k] == v)
                    flag = true
                // log
            }
            if (flag) break
            else {
                logger.debug("$v wait for key:$k")
                Thread.sleep(500)
            }
        }
    }
    
    fun unlock(k: String, v: String) {
        var flag = false
        while (true) {
            synchronized(this) {
                if (reqMap.containsKey(k) && reqMap[k] == v) {
                    reqMap.remove(k)
                    flag = true
                    logger.debug("$v release $k")
                } else {
                    logger.warn("$v 没抢到锁，无法释放")
                }
            }
            
            if (flag) break
            else {
                Thread.sleep(500)
                logger.debug("$v wait for key:$k")
            }
        }
    }
    
    fun process() {
        val k = "KEY"
        val v = IdHelper.objectIdStr()
        
        lock(k, v)
        
        try {
            // do something
            logger.debug("$v do some thing")
            Thread.sleep(3000)
            logger.debug("$v done")
        } finally {
            unlock(k, v)
        }
    }
}

fun main() {
    val t = StringUtils.join(listOf("A", "B", "C"), "-")
    println(t)
    
    val service = DuplicateFilterService()
    
    for (i in 0 until 3) {
        Executors.newCachedThreadPool().submit {
            println("第$i 次执行")
            service.process()
        }
    }
    
}