package com.example.kdemo.elevator.entity

class Elevator(
    var id: String,
    var currentFloor: Int,
    var up: Boolean,
    var passengers: MutableList<Passenger> = mutableListOf()
)