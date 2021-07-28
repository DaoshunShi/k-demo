package com.example.kdemo.entity

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
@Data
//@NoArgsConstructor
//@AllArgsConstructor
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0,
    var name: String = "",
    var address: String = ""
) {
//    constructor(name: String, email: String) : this(id = 0, name = name, email = email)
}