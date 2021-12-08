package com.example.kdemo

fun main() {

    test2()
}

fun test1() {
    val site1 = "AB-01-01-01"
    val site2 = "AB-02-01-01"
    val site3 = "AB-10-01-01"
    val site4 = "AB-1-02-01"
    
    println(site1.compareTo(site2))
    println(site1.compareTo(site3))
    println(site1.compareTo(site4))
    println(site2.compareTo(site3))
    println(site2.compareTo(site4))
    println(site3.compareTo(site4))
}

fun test2 () {
    val list = listOf<String>("A1", "B2", "C3", "D4")
    println(list)
    println(list.joinToString (","))
}