package com.example.kdemo.entity

import com.example.kdemo.helper.IdHelper

data class WmsDistrict(
    val id: String = IdHelper.objectIdStr(),
    
    val name: String = "",
    
    val warehouse: String = ""
) {
}

fun testDistricts(): List<WmsDistrict> {
    val r = mutableListOf<WmsDistrict>()
    for (i in 1..5) {
        r += WmsDistrict(name = "d$i", warehouse = "wh1")
    }
    return r
}