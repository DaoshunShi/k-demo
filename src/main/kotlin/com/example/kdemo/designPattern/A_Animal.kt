package com.example.kdemo.designPattern

abstract class Animal() {
    open var lived: Boolean = true
    
    abstract fun eat()
    
    abstract fun breath()
    
    abstract fun breed()
}

open class Bird : Animal() {
    open var canFly: Boolean = true
    
    override fun eat() {
        println("${javaClass.simpleName} eat")
    }
    
    override fun breath() {
        println("${javaClass.simpleName} breath")
    }
    
    override fun breed() {
        println("${javaClass.simpleName} breed")
    }
    
    open fun fly() {
        println("fly")
    }
}

class Goose : Bird() {
    override fun eat() {
        super.eat()
    }
    
    override fun breath() {
        super.breath()
    }
    
    override fun breed() {
        super.breed()
    }
}

class Duck : Bird() {
    override fun eat() {
        super.eat()
    }
    
    override fun breath() {
        super.breath()
    }
    
    override fun breed() {
        super.breed()
    }
}

class Penguin : Bird() {
    override var canFly: Boolean = false
    
    override fun eat() {
        super.eat()
    }
    
    override fun breath() {
        super.breath()
    }
    
    override fun breed() {
        super.breed()
    }
    
    override fun fly() {
        println("penguin cann't fly")
    }
}

fun main() {
    val goose = Goose()
    goose.fly()
    goose.eat()
    
    val penguin = Penguin()
    penguin.fly()
    penguin.eat()
}


