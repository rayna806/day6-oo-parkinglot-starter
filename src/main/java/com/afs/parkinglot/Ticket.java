package com.afs.parkinglot;

import com.afs.Car;

public record Ticket(Car car, Integer position, ParkingLot parkingLot) {

}
