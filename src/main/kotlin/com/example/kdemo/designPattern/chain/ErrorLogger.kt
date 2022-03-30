package com.example.kdemo.designPattern.chain

class ErrorLogger(level: Int) : AbstractLogger(level) {
    override fun write(msg: String) {
        println("Error Console::Logger: $msg");
    }
}