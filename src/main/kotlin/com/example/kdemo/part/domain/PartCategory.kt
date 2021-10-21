package com.example.kdemo.part.domain

import com.example.kdemo.helper.IdHelper
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class PartCategory(
    @Id
    var id: String = IdHelper.objectIdStr(),
    var name: String = ""
) {
}