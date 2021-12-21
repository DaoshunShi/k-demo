package com.example.kdemo.entity

import lombok.Data
import java.time.Instant
import javax.persistence.Entity
import javax.persistence.Id

@Entity
@Data
data class Book(
    @Id
    var id: Int = 0,
    
    var isbn: String = "",
    
    var category: String = "",
    
    var name: String = "",
    
    var author: String = "",
    
    var publisher: String = "",
    
    var publishTime: String = "",
    
    var version: String = "",
    
    var size: String = "",
    
    var pageNum: Int = 0
)
