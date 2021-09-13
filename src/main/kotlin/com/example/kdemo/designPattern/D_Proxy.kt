package com.example.kdemo.designPattern

abstract class Subject() {
    abstract fun request()
}

class RealSubject : Subject() {
    override fun request() {
        println("真实的请求")
    }
}

class Proxy : Subject() {
    private var realSubject: RealSubject? = null
    
    override fun request() {
        if (realSubject == null)
            realSubject = RealSubject()
        realSubject?.request()
    }
}

fun main() {
    val proxy = Proxy()
    proxy.request()
}