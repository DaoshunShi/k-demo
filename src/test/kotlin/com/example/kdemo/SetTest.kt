package com.example.kdemo

fun main() {
    
    var s = HashSet<String>()
    s.add("1")
    s.add("2")
    s.add("3")
    s.add("4")
    println(s.first())
    s.forEach { println(it) }
    
    val it = s.iterator()
    while (it.hasNext()) {
        println(it.next())
    }
    
    println(s.contains("1"))
}