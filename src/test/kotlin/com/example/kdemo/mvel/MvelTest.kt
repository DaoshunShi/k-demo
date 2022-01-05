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

fun test2() {
    val expression = "x = a"
    
    val vars = mutableMapOf<String, Any?>(
        "a" to 110,
        "b" to "BBB",
        "i" to 123,
        "s" to Person(name = "name"),
        "test1()" to test1()
    )
    val r = MVEL.eval(expression, vars)
    println(r)
}

fun test3() {
    val expression = "x = a; y = (a = a * 2) + 10; a"
    
    val vars = mutableMapOf<String, Any?>(
        "a" to 110,
        "b" to "BBB",
        "i" to 123,
        "s" to Person(name = "name"),
        "test1()" to test1()
    )
    val r = MVEL.eval(expression, vars)
    println(r)
}

fun testFunc() {
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

fun testStrIsBlank() {
    println("  \t\n".isBlank())
    // val expression = "str == null || str == ''"
    val expression = "str == empty"
    val vars = mutableMapOf<String, Any?>(
        "str" to "  \t\n"
    )
    val r = MVEL.eval(expression, vars)
    println(r)
}

fun testListIsEmpty() {
    val expression = "list != empty "
    val vars = mutableMapOf<String, Any?>(
        // "list" to emptyList<String>()
        "list" to listOf("  ")
    )
    val r = MVEL.eval(expression, vars)
    println(r)
}

fun testForeach() {
    val expression = "foreach (el : str) {" +
        "   System.out.print(\"[\"+ el + \"]\"); " +
        "}"
    val vars = mutableMapOf<String, Any?>(
        "str" to "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    )
    val r = MVEL.eval(expression, vars)
    println(r)
}

fun testArray() {
    val expression = "[{\"part\":\"abc\", \"qty\":1},{\"part\":\"test2\", \"qty\":23}]"
    val vars = mutableMapOf<String, Any>(
        "abc" to "Material-1"
    )
    val r = MVEL.eval(expression, vars)
    println(r)
}

fun testIfExpression() {
    val expression = "value > 0 ? true : false"
    val vars = mutableMapOf<String, Any?>(
        "value" to null
    )
    val r = MVEL.eval(expression, vars)
    println(r)
}

// failed
// fun testInsideFunc() {
//     val expression = "def someFunction(f_ptr) { f_ptr(); }" +
//         "var a = 10;" +
//         "someFunction(def { a * 10 });"
//     val r = MVEL.eval(expression)
//     println(r)
// }

// fun testLambda() {
//     val expression = "threshold = def (x) { x >= 10 ? x : 0 };" +
//         "result = cost + threshold(lowerBound);"
//     // val vars = mutableMapOf<String, Any?>("lowerBound" to 3, "cost" to 11)
//     val vars = mutableMapOf<String, Any?>("lowerBound" to 13, "cost" to 11)
//
//     val r = MVEL.eval(expression, vars)
//     println(r)
// }

fun testEmptyMap() {
    val expression1 = "inputVariables['testKey']"
    val expression2 = "inputVariables['testKey'] == empty"
    val expression3 = "inputVariables.testKey"
    val expression4 = "[] == empty"
    val vars = mutableMapOf<String, Any?>("inputVariables" to emptyMap<String, String>())
    
    val r1 = MVEL.eval(expression1, vars)
    println(r1)
    val r2 = MVEL.eval(expression2, vars)
    println(r2)
    // val r3 = MVEL.eval(expression3, vars)
    // println(r3)
    var r4 = MVEL.eval(expression4, vars)
    println(r4)
}

fun main() {
    // test4Func()
    // test4StrIsBlank()
    // testStrIsBlank()
    // test2()
    // println(StringUtils.length("asdfb"))
    // println(emptyList<String>().joinToString(","))
    // testForeach()
    // testInsideFunc()
    // testLambda()
    // testArray()
    // testIfExpression()
    testEmptyMap()
}
