package com.example.kdemo.entity

import com.example.kdemo.helper.IdHelper

data class WmsUnit(
    var id: String = IdHelper.objectIdStr(),
    
    var name: String = "",
    
    var parent: String = "",
    
    var conversionRate: Double = 0.0
) {

}
