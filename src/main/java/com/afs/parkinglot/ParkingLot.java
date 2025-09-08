package com.afs.parkinglot;

import com.afs.Car;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ParkingLot {
    private static final int CAPACITY = 10;
    private final Map<Integer, Car> parkedCars;
    private final Set<Ticket> usedTickets; // 追踪已使用的票据

    public ParkingLot() {
        this.parkedCars = new HashMap<>();
        this.usedTickets = new HashSet<>();
    }

    public Ticket park(Car car) {
        // 检查是否有可用位置
        if (parkedCars.size() >= CAPACITY) {
            System.out.println("No available position");
            return null;
        }

        // 找到下一个可用位置
        int position = findNextAvailablePosition();
        if (position == -1) {
            System.out.println("No available position");
            return null;
        }

        // 停车
        parkedCars.put(position, car);
        return new Ticket(car, position, this);
    }

    public Car fetch(Ticket ticket) {
        // 检查票据是否为null
        if (ticket == null ) {
            System.out.println("Unrecognized parking ticket");
            return null;
        }

        // 检查票据是否已经使用过 - 关键逻辑
        if (usedTickets.contains(ticket)) {
            System.out.println("Unrecognized parking ticket"); // 已使用票据输出错误消息
            return null;
        }

        // 检查停车位是否有车
        int position = ticket.position();
        Car car = parkedCars.get(position);

        // 验证票据是否正确 - record的equals()会自动比较所有字段
        if (car == null || !car.equals(ticket.car()) || !this.equals(ticket.parkingLot())) {
            System.out.println("Unrecognized parking ticket");
            return null;
        }

        // 取车成功后，将票据标记为已使用
        parkedCars.remove(position);
        usedTickets.add(ticket); // 关键：标记票据为已使用
        return car;
    }

    private int findNextAvailablePosition() {
        for (int i = 1; i <= CAPACITY; i++) {
            if (!parkedCars.containsKey(i)) {
                return i;
            }
        }
        return -1;
    }
}
