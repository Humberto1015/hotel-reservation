package service;

import model.Customer;
import model.IRoom;
import model.Reservation;
import model.Room;

import java.util.Date;
import java.util.LinkedList;

public class ReservationService {
    private static ReservationService INSTANCE;
    private static final LinkedList<Reservation> reservations = new LinkedList<>();
    private static final LinkedList<IRoom> availableRooms = new LinkedList<>();
    private ReservationService() {}

    public static ReservationService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ReservationService();
        }
        return INSTANCE;
    }
    public void addRoom(IRoom room) {
        availableRooms.add(new Room(room.getRoomNumber(), room.getRoomPrice(), room.getRoomType()));
    }
    public IRoom getARoom(String roomId) {
        IRoom matchRoom = null;
        for (IRoom room: availableRooms) {
            if (room.getRoomNumber().compareTo(roomId) == 0) {
                matchRoom = room;
                break;
            }
        }
        return matchRoom;
    }
    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reservation);
        return reservation;
    }

    public LinkedList<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        LinkedList<IRoom> matchRooms = new LinkedList<>();
        for (IRoom room: availableRooms) {
            boolean isBooked = false;
            for (Reservation reservation: reservations) {
                boolean isTheSameRoom = (room.getRoomNumber()).compareTo(reservation.room.getRoomNumber()) == 0;
                // skip the room if date has conflict
                if (isTheSameRoom && hasDateConflict(checkInDate, checkOutDate, reservation.checkInDate, reservation.checkOutDate)) {
                    isBooked = true;
                    break;
                }
            }
            if (!isBooked) {
                matchRooms.add(room);
            }
        }
        return matchRooms;
    }

    public LinkedList<Reservation> getCustomerReservation(Customer customer) {
        LinkedList<Reservation> res = new LinkedList<>();
        for (Reservation reservation: reservations) {
            if (customer.email.compareTo(reservation.customer.email) == 0) {
                res.add(reservation);
            }
        }
        return res;
    }

    public LinkedList<IRoom> getAllRooms() {
        return availableRooms;
    }

    public void printAllReservation() {
        System.out.println("[ Reservation List ]");
        if (reservations.isEmpty()) {
            System.out.println("< No reservation >");
            return;
        }
        for (Reservation reservation : reservations) {
            System.out.println(reservation.toString());
        }
    }

    boolean hasDateConflict(Date checkInA, Date checkOutA, Date checkInB, Date checkOutB) {
        boolean isCase1 = checkInA.before(checkInB) && (checkOutA.before(checkInB) || checkOutA.equals(checkInB));
        boolean isCase2 = (checkInA.after(checkOutB) || checkInA.equals(checkOutB)) && checkOutA.after(checkOutB);
        return !isCase1 && !isCase2;
    }
}
