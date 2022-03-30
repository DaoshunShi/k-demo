package com.example.kdemo.designPattern.chain

abstract class AbstractLogger(
    private val level: Int
) {
    private var nextLogger: AbstractLogger? = null
    
    fun setNextLogger(nl: AbstractLogger?) {
        nextLogger = nl
    }
    
    fun logMessage(l: Int, msg: String) {
        if (level <= l) {
            write(msg)
        }
        nextLogger?.logMessage(l, msg)
    }
    
    
    abstract fun write(msg: String)
    
    companion object {
        const val INFO = 1
        const val DEBUG = 2
        const val ERROR = 3
    }
    
}