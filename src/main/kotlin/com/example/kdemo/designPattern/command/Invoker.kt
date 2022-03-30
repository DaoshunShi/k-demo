package com.example.kdemo.designPattern.command

class Invoker(private val command: Command) {
    
    fun executeCommand() {
        command.execute()
    }
    
}