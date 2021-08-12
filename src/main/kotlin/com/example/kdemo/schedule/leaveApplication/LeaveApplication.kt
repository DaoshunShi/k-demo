package com.example.kdemo.schedule.leaveApplication

import java.time.Instant

data class LeaveApplication(
    var id: Int? = null,
    var proposerUsername: Long = 0L,
    var startTime: Instant = Instant.now(),
    var endTime: Instant = Instant.now(),
    var reason: String = "",
    var state: String = "",
    var disapprovedReason: String = "",
    var checkerUsername: Long = 0L,
    var checkTime: Instant = Instant.now()
)
