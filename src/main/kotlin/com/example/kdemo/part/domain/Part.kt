package com.example.kdemo.part.domain

import com.example.kdemo.helper.IdHelper
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Part(
    @Id
    var _id: String = IdHelper.objectIdStr(),
    var code: String = "",
    var name: String = "",
    var category: String = "",
    
    var spec: String = "",
    var vendor: String = ""
) {
}