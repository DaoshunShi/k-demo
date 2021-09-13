package com.example.kdemo.designPattern

abstract class Product {
    abstract fun operation()
}

class ConcreteProductA : Product() {
    override fun operation() {
        println("concreteProductA operation")
    }
    
    fun operationA() {
        println("concreteProductA operationA")
    }
}

class ConcreteProductB : Product() {
    override fun operation() {
        println("concreteProductB operation")
    }
    
    fun operationB() {
        println("concreteProductB operationB")
    }
}

class ConcreteProductC : Product() {
    override fun operation() {
        println("concreteProductC operation")
    }
    
    fun operationC() {
        println("concreteProductC operationC")
    }
}

abstract class Creator {
    abstract fun create(): Product
}

class ConcreteCreatorA : Creator() {
    override fun create(): ConcreteProductA {
        return ConcreteProductA()
    }
}

class ConcreteCreatorB : Creator() {
    override fun create(): ConcreteProductB {
        return ConcreteProductB()
    }
}

class ConcreteCreatorC : Creator() {
    override fun create(): ConcreteProductC {
        return ConcreteProductC()
    }
}

fun main() {
    val creatorA = ConcreteCreatorA()
    val productA = creatorA.create()
    
    val creatorB = ConcreteCreatorB()
    val productB = creatorB.create()
    
    val creatorC = ConcreteCreatorC()
    val productC = creatorC.create()
    
    productA.operation()
    productA.operationA()
    
    productB.operation()
    productB.operationB()
    
    productC.operation()
    productC.operationC()
}