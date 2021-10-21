package com.example.kdemo.inv.domain

import com.example.kdemo.helper.IdHelper
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class InvSummary(
    @Id
    var id: String = IdHelper.objectIdStr(),
    var part: String = "",
    var stockQty: Double = 0.0,
    var processingQty: Double = 0.0,
    var freezeQty: Double = 0.0
    
) {
}