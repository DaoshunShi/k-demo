package com.example.kdemo.elevator.service

import com.example.kdemo.elevator.entity.Elevator
import com.example.kdemo.elevator.entity.Passenger
import com.example.kdemo.schedule.DefaultExecutor
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit
import javax.annotation.PostConstruct

@Service
class ElevatorService {
    
    @Volatile
    var waitingPassengerMap = mutableMapOf<Int, MutableList<Passenger>>()
    
    @Volatile
    var elevatorSet = setOf<Elevator>()
    
    @PostConstruct
    fun init() {
        initElevator()
        
        for (elev in elevatorSet) {
            DefaultExecutor.serialScheduledExecutor.scheduleAtFixedRateSerially(
                elev.id,
                { process(elev) },
                3,
                2,
                TimeUnit.SECONDS
            )
        }
        
    }
    
    fun initElevator() {
        elevatorSet = mutableSetOf(
            Elevator("elev-1", 1, true),
            Elevator("elev-2", 1, true),
            Elevator("elev-3", 1, true)
        )
    }
    
    fun process(elevator: Elevator) {
        // passengers get off
        val arrivedPassenger = elevator.passengers.filter { it.toFloor == elevator.currentFloor }
        getOff(elevator, arrivedPassenger)
        
        // passengers get in
        val startPassenger = waitingPassengerMap[elevator.currentFloor]?.filter { it -> elevator.up == (it.toFloor > it.fromFloor) }
            ?: emptyList()
        getIn(elevator, startPassenger)
        
        // move
        if (needToMove(elevator))
            elevator.currentFloor += if (elevator.up) 1 else -1
    }
    
    fun call(floor: Int, passenger: Passenger) {
        val l = waitingPassengerMap.getOrElse(floor) { emptyList() } + passenger
        waitingPassengerMap[floor] = l as MutableList<Passenger>
    }
    
    fun getOff(elevator: Elevator, passengers: List<Passenger>) {
        elevator.passengers = elevator.passengers.filterNot { passengers.contains(it) }.toMutableList()
    }
    
    fun getIn(elevator: Elevator, passengers: List<Passenger>) {
        elevator.passengers.addAll(passengers)
    }
    
    // TODO ?????????????????????
    fun needToMove(elevator: Elevator): Boolean {
        val upPassengers = elevator.passengers.filter { it.toFloor > elevator.currentFloor }
        val downPassengers = elevator.passengers.filter { it.toFloor < elevator.currentFloor }
        val higher = waitingPassengerMap.filter { it.key > elevator.currentFloor }.filter { it.value.isNotEmpty() }
        val lower = waitingPassengerMap.filter { it.key < elevator.currentFloor }.filter { it.value.isNotEmpty() }
        
        // ?????????
        if (elevator.up) {
            return if (upPassengers.isEmpty() && downPassengers.isEmpty() && higher.isNotEmpty()) {
                // ?????????????????????????????????????????????????????????
                true
            } else if (upPassengers.isNotEmpty()) {
                // ????????????????????????
                true
            } else if (upPassengers.isNullOrEmpty() && downPassengers.isNotEmpty()) {
                // ?????????????????????????????????????????????????????????
                elevator.up = false
                true
            } else {
                false
            }
        }
        // ?????????
        else {
            return if (upPassengers.isEmpty() && downPassengers.isEmpty() && lower.isNotEmpty()) {
                // ?????????????????????????????????????????????????????????
                true
            } else if (downPassengers.isNotEmpty()) {
                // ????????????????????????
                true
            } else if (downPassengers.isNullOrEmpty() && upPassengers.isNotEmpty()) {
                // ?????????????????????????????????????????????????????????
                elevator.up = true
                true
            } else {
                false
            }
        }
    }
    
    
}

