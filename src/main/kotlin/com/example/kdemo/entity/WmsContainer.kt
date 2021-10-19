package com.example.kdemo.entity

import com.example.kdemo.helper.IdHelper
import java.text.DecimalFormat

data class WmsContainer(
    val id: String = IdHelper.objectIdStr(),
    
    var type: String = "",
    
    var site: String = "",
    
    var code: String = ""
)

fun testContainers(): List<WmsContainer> {
    val r = mutableListOf<WmsContainer>()
    for (i in 1..100) {
        r += WmsContainer(type = "ct1", site = "", code = "LX${DecimalFormat("000").format(i)}")
    }
    return r
}