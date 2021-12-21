package com.example.kdemo.entity

import lombok.Data
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
@Data
data class BookRecord(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0,
    
    var book: Int,
    
    var no: Int = 1,
    
    var borrowed: Boolean = false,
    
    var borrowedBy: String = ""
)