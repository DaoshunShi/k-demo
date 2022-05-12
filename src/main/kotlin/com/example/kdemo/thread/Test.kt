package com.example.kdemo.thread


class Producer : Runnable {
    var lock = Object()
    var sb = StringBuffer("")
    var done = false
    override fun run() {
        synchronized(lock) {
            println("A")
            for (i in 0..9) {
                try {
                    sb.append("$i ")
                    println("Appending ... ")
                } catch (e: Exception) {
                }
            }
            lock.notify()
            done = true
        }
    }
    
}

class Consumer(var p: Producer) : Runnable {
    override fun run() {
        println("Rreached")
        synchronized(p.lock) {
            println("B")
            try {
                while (!p.done) {
                    p.lock.wait()
                }
            } catch (e: Exception) {
            }
            println(p.sb)
        }
    }
}


fun main(args: Array<String>) {
    val p = Producer()
    val c = Consumer(p)
    val t1 = Thread(p)
    val t2 = Thread(c)
    t2.start()
    Thread.sleep(100)
    t1.start()
}