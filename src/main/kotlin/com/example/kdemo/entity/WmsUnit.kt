package com.example.kdemo.entity

import com.example.kdemo.helper.IdHelper
import lombok.Data
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
@Data
data class WmsUnit(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: String = IdHelper.objectIdStr(),
    
    var name: String = "",
    
    var parent: String = "",
    
    var conversionRate: Double = 0.0
)