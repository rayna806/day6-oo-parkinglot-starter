package com.afs.parkinglot;

import com.afs.Car;
import java.util.List;
import java.util.Arrays;

public class StandardParkingBoy {
    private List<ParkingLot> parkingLots;

    public StandardParkingBoy(ParkingLot parkingLot) {
        this.parkingLots = Arrays.asList(parkingLot);
    }

    public StandardParkingBoy(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
    }

    public Ticket park(Car car) {
        // 按顺序尝试每个停车场，优先使用第一个有可用位置的停车场
        for (ParkingLot parkingLot : parkingLots) {
            Ticket ticket = parkingLot.park(car);
            if (ticket != null) {
                return ticket;
            }
        }
        // 如果所有停车场都满了，使用第一个停车场来输出错误消息
        return parkingLots.get(0).park(car);
    }

    public Car fetch(Ticket ticket) {
        // 尝试从每个停车场取车
        for (ParkingLot parkingLot : parkingLots) {
            Car car = parkingLot.fetch(ticket);
            if (car != null) {
                return car;
            }
        }
        // 如果在所有停车场都找不到，使用第一个停车场来输出错误消息
        return parkingLots.get(0).fetch(ticket);
    }
}
