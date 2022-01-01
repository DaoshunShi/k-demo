package com.example.kdemo.thread

val lock = Object()
var done = false

fun test1() {
    val t1 = Thread {
        synchronized(lock) {
            println("A")
            for (i in 0..9) {
                try {
                    println("Appending i")
                } catch (e: Exception) {
                }
            }
            lock.notify()
            done = true
        }
    }
    
    val t2 = Thread {
        println("Rreached")
        synchronized(lock) {
            println("B")
            try {
                while (!done) {
                    lock.wait()
                }
            } catch (e: Exception) {
            }
            println("t2 done")
        }
    }
    
    t2.start()
    Thread.sleep(10)
    t1.start()
}

fun test2() {
    val t1 = Thread {
        synchronized(lock) {
            println("A start")
            lock.notifyAll()
            try {
                synchronized(lock) {
                    println("A wait")
                    lock.wait()
                }
            } catch (e: InterruptedException) {
            }
            println("A")
        }
    }
    
    val t2 = Thread {
        synchronized(lock) {
            println("B start")
            while (true) {
                try {
                    synchronized(lock) {
                        println("B wait")
                        lock.wait()
                    }
                } catch (e: InterruptedException) {
                }
                println("B")
                lock.notifyAll()
            }
        }
    }
    
    t2.start()
    Thread.sleep(10)
    t1.start()
}


fun test3() {
    val lock3 = Object()
    
    val t1 = Thread {
        println("A start")
        while (true) {
            try {
                synchronized(lock3) {
                    println("A wait")
                    lock3.notifyAll()
                    lock3.wait()
                }
            } catch (e: InterruptedException) {
            }
            println("A")
        }
    }
    
    val t2 = Thread {
        while (true) {
            println("B start")
            try {
                synchronized(lock3) {
                    println("B wait")
                    lock3.notify()
                    lock3.wait()
                }
            } catch (e: InterruptedException) {
            }
            println("B")
            
        }
    }
    
    t1.start()
    Thread.sleep(10)
    t2.start()
    
}


fun test4() {
    val l = Object()
    var s = "A"
    
    val t1 = Thread {
        println("A start")
        while (true) {
            try {
                synchronized(l) {
                    l.notifyAll()
                    if (s == "A") {
                        println(s)
                        s = "B"
                    }
                    Thread.sleep(500)
                    println("A wait")
                    l.wait()
                }
            } catch (e: InterruptedException) {
            }
        }
    }
    
    val t2 = Thread {
        while (true) {
            println("B start")
            try {
                synchronized(l) {
                    l.notifyAll()
                    if (s == "B") {
                        println(s)
                        s = "C"
                    }
                    Thread.sleep(500)
                    println("B wait")
                    l.wait()
                }
            } catch (e: InterruptedException) {
            }
            println("B")
            
        }
    }
    
    val t3 = Thread {
        while (true) {
            println("C start")
            try {
                synchronized(l) {
                    l.notifyAll()
                    if (s == "C") {
                        println(s)
                        s = "A"
                    }
                    Thread.sleep(500)
                    println("C wait")
                    l.wait()
                }
            } catch (e: InterruptedException) {
            }
            println("C")
        }
    }
    
    t1.start()
    Thread.sleep(10)
    t2.start()
    Thread.sleep(10)
    t3.start()
}

fun main() {
    // test1()
    // test2()
    // test3()
    test4()
}