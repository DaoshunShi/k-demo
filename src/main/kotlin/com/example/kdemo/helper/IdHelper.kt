package com.example.kdemo.helper

import org.bson.types.ObjectId

object IdHelper {
    
    fun objectIdStr(): String {
        return ObjectId().toHexString().uppercase()
    }
}