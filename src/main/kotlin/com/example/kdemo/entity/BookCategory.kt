package com.example.kdemo.entity

import lombok.Data
import javax.persistence.Entity
import javax.persistence.Id

@Entity
@Data
data class BookCategory(
    @Id
    var id: Int = 0,
    
    var isbn: String = "",
    
    var name: String = "",
    
    var parentCategory: String = ""
)