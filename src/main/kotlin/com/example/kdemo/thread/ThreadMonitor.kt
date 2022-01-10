package com.example.kdemo.thread

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.concurrent.*

@Service
class ThreadMonitor {
    
    private val logger = LoggerFactory.getLogger(javaClass)
    
    val scheduledExecutors: ScheduledExecutorService = Executors.newScheduledThreadPool(10)
    
    
    @Volatile
    var t = false
    
    fun test1(s: String) {
        
        // 任务失败
        logger.info("任务失败")
        
        // 添加监控 a
        // 监控回调 A
        val q = scheduledExecutors.submit { queryTaskStatus() }
        // val p = scheduledExecutors.submit { query2() }
        val p: Future<*>? = null
        
        // println("----------")
        // println(p?.isDone == true)
        // println(p?.cancel(true))
        
        println(q.isDone)
        // Thread.sleep(500)
        // if (!q.isDone)
        //     q.cancel(true)
        
        // while(true) 等待选择
        // 如果收到了 A，那么停止 while(true)
        // 如果 while(true) 发出去了，是否停掉 a
        while (!t) {
            logger.info("while(true) wait -- ")
            Thread.sleep(400)
        }
        // q.cancel(true)
        
        println(q.isDone)
        
        logger.warn("END")
        // scheduledExecutors.shutdown()
    }
    
    private fun queryTaskStatus() {
        for (i in 1..10) {
            logger.info("$i")
            Thread.sleep(500)
        }
        notifyMain()
    }
    
    private fun notifyMain() {
        t = true
        logger.info("t = $t")
    }
    
    private fun query2() {
        while (true) {
            logger.info("test")
            Thread.sleep(500)
        }
    }
    
    fun test2(s: String) {
        logger.info("任务失败")
        val p = scheduledExecutors.scheduleAtFixedRate(
            { queryTaskStatus() }, 200, 600, TimeUnit.MILLISECONDS
        )
        
        Thread.sleep(20000)
        println(p.isDone)
        println(p.cancel(true))
    }
    
    fun test3() {
        logger.info("任务失败")
        val ft = FutureTask {
            scheduledExecutors.submit { queryTaskStatus() }
        }
        logger.info("begin to wait")
        ft.get()
        logger.info("end")
    }
    
    fun test4() {
        // testForMutFun() { t2("B") }
        // Thread.sleep(5000)
        
        val m = { scheduledExecutors.submit { queryTaskStatus() } }
        val n = testForMutFun(m) { t2("B") }
        n?.get()
        
        println(n?.isDone)
        
        while (!t) {
            println("t = $t")
            Thread.sleep(500)
        }
        Thread.sleep(500)
        println(n?.isDone)
    }
    
    private fun t2(a: String) {
        logger.info("t2: $a")
    }
    
    fun testForMutFun(monitorFuture: (() -> Future<*>?)? = null, t2: () -> Unit): Future<*>? {
        logger.info("test for multi function")
        val r = monitorFuture?.let { monitorFuture() }
        t2()
        
        return r
    }
    
    fun queryTaskStatus2(): Boolean {
        return true
    }
    
    fun test5() {
        var t1: Boolean = false
        logger.info("任务失败")
        val future = { scheduledExecutors.submit { t1 = queryTaskStatus2() } }
        val f2 = future()
        println(f2.get())
        println(f2.isDone)
        println(t1)
    }
    
    fun test6() {
        var t1 = false
        logger.info("失败")
        testWrap({scheduledExecutors.submit{
            val r = true
            t1 = r
        }}) {
            logger.info("worker")
        }
        println(t1)
    }
    
    private fun testWrap(future: (() -> Future<*>?)? = null, worker: () -> Unit) {
        worker()
    
        val failure = future?.let { it() }
        while (failure?.isDone != true) {
            Thread.sleep(300)
        }
    }
    
}

fun main() {
    val tm = ThreadMonitor()
    // tm.test1()
    // tm.test2()
    // tm.test3()
    // tm.test4()
    // tm.test5()
    tm.test6()
}