package com.example.kdemo

fun main() {
    val a = false
    var b = 1
    while (true) {
        if (a) if (b++ < 10) {
            println(b)
        } else continue
        
        println("-----------")
        
        if (b >= 10) break
    }
    
}