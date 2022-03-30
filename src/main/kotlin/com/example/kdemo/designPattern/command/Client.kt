package com.example.kdemo.designPattern.command

fun main() {
    val r = Receiver()
    val c = ConcreteCommand(r)
    val i = Invoker(c)
    
    i.executeCommand()
    
    println("Command pattern end")
}