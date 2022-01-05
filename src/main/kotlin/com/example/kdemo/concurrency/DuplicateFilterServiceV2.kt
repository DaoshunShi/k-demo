package com.example.kdemo.concurrency

import com.example.kdemo.helper.IdHelper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class DuplicateFilterServiceV2 {
    private val logger = LoggerFactory.getLogger(javaClass)
    
    private val reqMap: MutableMap<String, String> = ConcurrentHashMap<String, String>()
    
    private val lock = Object()
    
    fun lock(k: String, v: String) {
        logger.debug("$this")
        var flag = false
        while (true) {
            synchronized(lock) {
                if (!reqMap.containsKey(k)) {
                    reqMap[k] = v
                    flag = true
                    logger.debug("$v get lock $k")
                } else if (reqMap[k] == v)
                    flag = true
                else
                    lock.wait()
            }
            if (flag) break
            else {
                logger.debug("$v wait for key:$k")
            }
        }
    }
    
    fun unlock(k: String, v: String) {
        synchronized(lock) {
            if (reqMap.containsKey(k) && reqMap[k] == v) {
                reqMap.remove(k)
                lock.notifyAll()
                logger.debug("$v release $k")
            } else {
                logger.warn("$v 没抢到锁，无法释放")
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
    
    val service = DuplicateFilterServiceV2()
    
    Thread { service.process() }.start()
    Thread { service.process() }.start()
    
}