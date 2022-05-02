package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class AdminResource {
    private static final CustomerService customerService = CustomerService.getInstance();
    private static final ReservationService reservationService = ReservationService.getInstance();
    private static final HashSet<String> existingRoomNumbers = new HashSet<>();

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }
    public void addRoom(List<IRoom> rooms) {
        for (IRoom room: rooms) {
            reservationService.addRoom(room);
        }
    }
    public LinkedList<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }
    public LinkedList<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }
    public void displayAllReservations() {
        reservationService.printAllReservation();
    }
    public boolean roomNumberDoesExist(String roomNumber) {
        return existingRoomNumbers.contains(roomNumber);
    }
    public void recordRoomNumber(String roomNumber) {
        existingRoomNumbers.add(roomNumber);
    }
}
