package com.example.kdemo.entity

import com.example.kdemo.helper.IdHelper

data class WmsPart(
    val id: String = IdHelper.objectIdStr(),
    
    var name: String = "",
    
    var spec: String = "",
    
    var category: String = ""

) {
}

fun testParts(): List<WmsPart> {
    val r = mutableListOf<WmsPart>()
    for (i in 1..10) {
        r += WmsPart(name = "p$i", spec = "ps$i", category = "c1")
    }
    return r
}