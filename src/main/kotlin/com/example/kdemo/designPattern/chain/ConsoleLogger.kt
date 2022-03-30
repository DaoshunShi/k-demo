package com.example.kdemo.designPattern.chain


class ConsoleLogger(level: Int) : AbstractLogger(level) {
    override fun write(msg: String) {
        println("Standard Console::Logger: $msg")
    }
}