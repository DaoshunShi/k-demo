package com.example.kdemo

import java.util.regex.Matcher
import java.util.regex.Pattern

fun main() {
    // fun1()
    fun2()
}

fun fun1() {
    val msg = "Duplicate entry '1234' for key 'wms_warehouse.UK_5vgfkcoe5gis7e8tclk0yydj3'"
    val pattern = "Duplicate entry '[\\u4E00-\\u9FA5A-Za-z0-9_]+' for key '[\\u4E00-\\u9FA5A-Za-z0-9_.]+'"
    
    val r: Pattern = Pattern.compile(pattern)
    val m: Matcher = r.matcher(msg)
    
    println(m)
    if (m.matches()) {
        val pattern2 = "'[\\u4E00-\\u9FA5A-Za-z0-9_.]+'"
        val r2: Pattern = Pattern.compile(pattern2)
        val m2 = r2.matcher(msg)
        println(m2)
        while (m2.find()) {
            println(m2.group())
        }
    }
}

fun fun2() {
    val msg = "AB-123456-098765-0.1"
    val pattern = "^AB-(\\w{6})-(\\w{6})-(\\d+.\\d+)\$"
    
    val r: Pattern = Pattern.compile(pattern, 1)
    val m: Matcher = r.matcher(msg)
    
    if (m.matches()) {
        println(m.group(0))
        println(m.group(1))
        println(m.group(2))
        println(m.group(3))
    }
}