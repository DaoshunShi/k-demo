package com.example.kdemo

fun funWhen1(): String {
    return "fun13"
}

fun main() {
    when (funWhen1()) {
        "fun1" -> println("fun1")
        "fun2" -> println("fun2")
        "fun3" -> println("fun3")
        else -> println("else")
    }
    
    when (val s = funWhen1()) {
        "fun1" -> println("fun1")
        "fun2" -> println("fun2")
        "fun3" -> println("fun3")
        else -> println("else s = $s")
    }
}