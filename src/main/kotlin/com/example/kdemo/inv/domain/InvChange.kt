package com.example.kdemo.inv.domain

import com.example.kdemo.helper.IdHelper
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class InvChange(
    @Id
    var id: String = IdHelper.objectIdStr(),
    var part: String = "",
    var qty: Double = 0.0,
    var fromBin: String = "",
    var toBin: String = ""
) {
}