package com.example.kdemo.mvel

import org.mvel2.MVEL


data class Person(
    var name: String
)

fun person(name: String): Person {
    return Person(name)
}

fun test1() {
    println("test1")
}

fun main() {
    // val expression = "x = a; y = (a = a * 2) + 10; a"
    // val expression = "test1()"
    //
    // val vars = mutableMapOf<String, Any?>("a" to 110, "b" to "BBB", "i" to 123, "s" to WmsSite(id = "1234"), "test1()" to test1())
    // // val vars = mutableMapOf<String, Any?>()
    // val digest = MVEL.eval(expression, test1())
    // println(digest)
    
    println("3".padStart(3, '0'))
    val expression = "padIntStr(3, 5)"
    val r = MVEL.eval(expression, PadStrHelper())
    println(r)

}

class PadStrHelper() {
    fun padIntStr(i: Int, len: Int): String {
        println("i is $i")
        return i.toString().padStart(len, '0')
    }
}