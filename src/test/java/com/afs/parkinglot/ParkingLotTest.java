package com.afs.parkinglot;

import com.afs.Car;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class ParkingLotTest {
    //story 1
    //Given a parking lot and a car,When parking a car,Then return a parking ticket
    @Test
    public void should_return_parking_ticket_when_parking_a_car(){
        ParkingLot parkingLot = new ParkingLot();
        Car car = new Car("park number 1");
        Ticket ticket = new Ticket(car,1,parkingLot);
        Ticket ticketResult = parkingLot.park(car);
        assertEquals(ticket,ticketResult);
    }

    //Given a parking lot with a parked car and a parking ticket,When fetch the car,Then return the parking car
    @Test
    public void should_return_car_when_fetch_a_car(){
        ParkingLot parkingLot = new ParkingLot();
        Car car = new Car("park number 1");
        Ticket ticketResult = parkingLot.park(car);
        Car carResult = parkingLot.fetch(ticketResult);
        assertEquals(car,carResult);
    }

    //Given a parking lot with two parked cars, and two parking tickets, When fetch the car twice, Then return the right car with each ticket
    @Test
    public void should_return_car_when_fetch_all_cars(){
        ParkingLot parkingLot = new ParkingLot();
        Car car1 = new Car("park number 1");
        Car car2 = new Car("park number 2");
        Ticket ticketResult1 = parkingLot.park(car1);
        Ticket ticketResult2 = parkingLot.park(car2);
        Car carResult1 = parkingLot.fetch(ticketResult1);
        Car carResult2 = parkingLot.fetch(ticketResult2);
        assertEquals(car1,carResult1);
        assertEquals(car2,carResult2);
    }

    //Given a parking lot, and a wrong parking ticket, When fetch the car, Then return nothing.
    @Test
    public void should_return_null_when_fetch_a_car_with_wrong_ticket(){
        ParkingLot parkingLot = new ParkingLot();
        Car car = new Car("park number 1");
        Ticket ticketResult = parkingLot.park(car);
        Ticket wrongTicket = new Ticket(new Car("park number 2"),2,parkingLot);
        Car carResult = parkingLot.fetch(wrongTicket);
        assertNull(carResult);
    }

    //Given a parking lot, and a used parking ticket, When fetch the car, Then return nothing.
    @Test
    public void should_return_null_when_fetch_a_car_with_used_ticket(){
        ParkingLot parkingLot = new ParkingLot();
        Car car = new Car("park number 1");
        Ticket ticketResult = parkingLot.park(car);
        Car carResult = parkingLot.fetch(ticketResult);
        Car carResult1 = parkingLot.fetch(ticketResult);
        assertNull(carResult1);
    }

    //Given a parking lot without any position, and a car, When park the car, Then return nothing
    @Test
    public void should_return_null_when_park_a_car_with_no_position(){
        ParkingLot parkingLot = new ParkingLot();
        // 先填满所有停车位
        for(int i = 0; i < 10; i++) {
            Car car = new Car("car " + i);
            parkingLot.park(car);
        }

        // 尝试在没有可用位置时停车
        Car car = new Car("park number 11");
        Ticket ticketResult = parkingLot.park(car);
        assertNull(ticketResult);
    }

    //Story 2
    // Given a parking lot, and an unrecognized ticket, When fetch the car, Then return nothing with error message "Unrecognized parking ticket."
    @Test
    public void should_give_message_when_fetch_a_car_with_unrecognized_ticket(){
        //Given
        ParkingLot parkingLot = new ParkingLot();
        Car car = new Car("park number 1");
        Ticket ticketResult = parkingLot.park(car);
        Ticket wrongTicket = new Ticket(new Car("park number 2"),2,parkingLot);
        //When
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        parkingLot.fetch(wrongTicket);
        //Then
        assertTrue(outputStream.toString().contains("Unrecognized parking ticket"));

        // 恢复标准输出
        System.setOut(System.out);
    }

    // Given a parking lot, and a used ticket, When fetch the car, Then return
    //nothing with error message "Unrecognized parking ticket."
    @Test
    public void should_give_message_when_park_a_car_with_used_ticket(){
        //Given
        ParkingLot parkingLot = new ParkingLot();
        Car car = new Car("park number 1");
        Ticket ticketResult = parkingLot.park(car);
        Car carResult = parkingLot.fetch(ticketResult);
        //When
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        parkingLot.fetch(ticketResult);
        //Then
        assertTrue(outputStream.toString().contains("Unrecognized parking ticket"));

        // 恢复标准输出
        System.setOut(System.out);
    }
    //Given a parking lot without any position, and a car, When park the car, Then
    //return nothing with error message "No available position."
    @Test
    public void should_give_message_when_park_a_car_with_no_position(){
        //Given
        ParkingLot parkingLot = new ParkingLot();
        // 先填满所有停车位
        for(int i = 0; i < 10; i++) {
            Car car = new Car("car " + i);
            parkingLot.park(car);
        }

        //When
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Car car = new Car("park number 11");
        Ticket ticketResult = parkingLot.park(car);

        //Then
        assertNull(ticketResult);
        assertTrue(outputStream.toString().contains("No available position"));

        // 恢复标准输出
        System.setOut(System.out);
    }
}
