package com.example.kdemo.entity

import com.example.kdemo.helper.IdHelper

data class WmsSite(
    
    var id: String = IdHelper.objectIdStr(),
    
    var warehouse: String = "",
    
    var district: String = "",
    
    var row: Int = 0,
    
    var column: Int = 0,
    
    var layer: Int = 0,
    
    var depth: Int = 0,
    
    val container: String = ""

) {
}

fun testSites(): List<WmsSite> {
    val r = mutableListOf<WmsSite>()
    for (row in 1..20) {
        for (column in 1..10) {
            for (layer in 1..10) {
                r += WmsSite(
                    warehouse = "wh1", district = "d1", row = 1, column = 1, layer = 1, depth = 0,
                    container = "C-$row-$column"
                )
            }
            
        }
    }
    return r
}