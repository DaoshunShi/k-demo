package com.example.kdemo

import java.util.regex.Matcher
import java.util.regex.Pattern

fun main() {
    // val line = "        BindBinContainerBp::class.simpleName!!, \"绑定容器和库位\", inputParams = listOf("
    // val s = "dict.lo(\"key0\")"
    // val str = "绑定容器和库位"
    // println(line.replace(str, s))
    // fun1()
    // fun2()
    test3()
}

fun fun1() {
    val msg = "Duplicate entry '1234' for key 'wms_warehouse.UK_5vgfkcoe5gis7e8tclk0yydj3'"
    // val pattern = "Duplicate entry '[\\u4E00-\\u9FA5A-Za-z0-9_]+' for key '[\\u4E00-\\u9FA5A-Za-z0-9_.]+'"
    val pattern = "entry '[\\u4E00-\\u9FA5A-Za-z0-9_]+' for key '[\\u4E00-\\u9FA5A-Za-z0-9_.]+'"
    
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
    
    val pattern3 = "'[\\u4E00-\\u9FA5A-Za-z0-9_.]+'"
    val r3: Pattern = Pattern.compile(pattern3)
    val m3 = r3.matcher(msg)
    println(m3)
    while (m3.find()) {
        println(m3.group())
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

fun test3() {
    val str = "!@#)(*&(*%&"
    val pattern = ".{8,100}"
    
    val r = Pattern.compile(pattern)
    val m = r.matcher(str)
    println(m.matches())
}