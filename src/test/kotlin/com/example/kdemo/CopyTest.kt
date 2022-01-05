package com.example.kdemo

fun testDeepCopy() {
    val l1 = mutableListOf("a", "b", "c")
    val l2 = l1.map { it }.toMutableList()
    println(l1)
    println(l2)
    l1 += "d"
    println(l1)
    println(l2)
    l2 += "e"
    println(l1)
    println(l2)
}

fun testShallowCopy() {
    val l1 = mutableListOf("a", "b", "c")
    val l2 = l1
    println(l1)
    println(l2)
    l1 += "d"
    println(l1)
    println(l2)
}


fun main() {
    testDeepCopy()
}