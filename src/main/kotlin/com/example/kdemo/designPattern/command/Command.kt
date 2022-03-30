package com.example.kdemo.designPattern.command

abstract class Command(private var receiver: Receiver? = null) {
    
    abstract fun execute()
    
}