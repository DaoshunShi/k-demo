package com.example.kdemo.entity

import com.example.kdemo.helper.IdHelper
import java.time.Instant
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id


@Entity
data class WmsOrderLog(
    @Id
    @Column(length = 50)
    var id: String = IdHelper.objectIdStr(),
    
    var createdOn: Instant = Instant.now(),
    
    @Column(length = 50)
    var orderId: String,
    
    var level: WmsOrderLogLevel = WmsOrderLogLevel.Info,
    
    @Column(length = ContentMaxLen)
    var content: String,
) {
    
    companion object {
        
        const val ContentMaxLen = 2000
        
    }
    
}

enum class WmsOrderLogLevel {
    Info, Error
}