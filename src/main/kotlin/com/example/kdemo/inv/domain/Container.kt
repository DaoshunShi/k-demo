package com.example.kdemo.inv.domain

import com.example.kdemo.helper.IdHelper
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Container(
    @Id
    var _id: String = IdHelper.objectIdStr(),
    var category: String = "",
    var bin: String = "",
    var state: String = "",
    var occupied: Boolean = false,
    var occupiedReason: String = ""
) {
}