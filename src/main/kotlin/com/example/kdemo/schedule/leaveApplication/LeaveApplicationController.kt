package com.example.kdemo.schedule.leaveApplication

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.time.temporal.ChronoUnit

@RestController
@RequestMapping("/api/leave")
class LeaveApplicationController(
    val leaveApplicationService: LeaveApplicationService
) {
    
    @GetMapping("add")
    @ResponseStatus(HttpStatus.CREATED)
    fun add() {
        val req = LeaveApplication(
            id = 1, proposerUsername = 1, startTime = Instant.now().plus(10, ChronoUnit.SECONDS),
            endTime = Instant.now().plus(20, ChronoUnit.SECONDS), reason = "reason", state = "start",
            disapprovedReason = "disApproveReason", checkerUsername = 2, checkTime = Instant.now()
        )
        leaveApplicationService.addJobAndTrigger(req)
    }
}