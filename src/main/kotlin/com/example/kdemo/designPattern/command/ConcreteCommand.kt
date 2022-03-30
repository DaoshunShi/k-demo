package com.example.kdemo.designPattern.command

class ConcreteCommand(private val receiver: Receiver) : Command(receiver) {
    override fun execute() {
        receiver.action()
    }
}