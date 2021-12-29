package com.example.kdemo.elevator.service

import com.example.kdemo.elevator.entity.Passenger
import org.springframework.stereotype.Service

@Service
class ElevatorMockTaskService {
    
    @Volatile
    var passengerMap = mutableMapOf<Int, List<Passenger>>()
    
    fun addPassenger() {}
    
    fun travel() {}
    
}