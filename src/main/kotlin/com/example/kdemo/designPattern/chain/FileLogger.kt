package com.example.kdemo.designPattern.chain

import java.sql.DriverManager.println

class FileLogger(level: Int) : AbstractLogger(level) {
    override fun write(msg: String) {
        println("File::Logger: $msg")
        
    }
}