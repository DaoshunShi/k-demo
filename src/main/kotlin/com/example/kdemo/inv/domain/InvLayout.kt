package com.example.kdemo.inv.domain

import com.example.kdemo.helper.IdHelper
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class InvLayout(
    @Id
    var id: String = IdHelper.objectIdStr(),
    
    var part: String = "",
    var qty: Double = 0.0,
    var bin: String = "",
    var container: String = "",
    var state: Int = 0  // 0 正常， 1 锁定， 2 冻结
) {
}