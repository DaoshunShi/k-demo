package com.example.kdemo.designPattern

abstract class Component {
    abstract fun operation()
}

class ConcreteComponent : Component() {
    override fun operation() {
        println("具体对象的操作")
    }
}

abstract class Decorator(open var component: Component) : Component() {
    override fun operation() {
        component.operation()
    }
}

class ConcreteDecoratorA(override var component: Component) : Decorator(component) {
    override fun operation() {
        component.operation()
        println("A 的操作")
    }
}

class ConcreteDecoratorB(override var component: Component) : Decorator(component) {
    override fun operation() {
        component.operation()
        println("B 的操作")
    }
}

fun main() {
    val c = ConcreteComponent()
    val d1 = ConcreteDecoratorA(c)
    val d2 = ConcreteDecoratorB(d1)
    d2.operation()
}

