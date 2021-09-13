package com.example.kdemo.designPattern

abstract class Strategy {
    abstract fun algorithmInterface()
}

class ConcreteStrategyA : Strategy() {
    override fun algorithmInterface() {
        println("stg A")
    }
}

class ConcreteStrategyB : Strategy() {
    override fun algorithmInterface() {
        println("stg B")
    }
}

class ConcreteStrategyC : Strategy() {
    override fun algorithmInterface() {
        println("stg C")
    }
}

class Context(var stg: Strategy) {
    fun contextInterface() {
        stg.algorithmInterface()
    }
}

fun main() {
    var context = Context(ConcreteStrategyA())
    context.contextInterface()
    
    context = Context(ConcreteStrategyA())
    context.contextInterface()
    
    context = Context(ConcreteStrategyC())
    context.contextInterface()
}