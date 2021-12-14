package com.example.kdemo.entity

import lombok.Data
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
@Data
data class InvOrder(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = 0,
    var part: String = "",
    var qty: Double = 0.0,
    val user: String = "",
    var reserved: Boolean = false
)
