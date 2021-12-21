package com.example.kdemo.entity

import lombok.Data
import java.time.Instant
import javax.persistence.Entity
import javax.persistence.Id

@Entity
@Data
class BookBorrowRecord(
    @Id
    var id: Int = 0,
    
    var user: String = "",
    
    var bookRecord: Int = 0,
    
    var borrowTime: Instant = Instant.now(),
    
    var originalReturnTime: Instant? = null,
    
    var exceptedReturnTime: Instant? = null,
    
    var actualReturnTime: Instant? = null,
    
    var penalty: Double = 0.0,
    
    var returned: Boolean = false
)
