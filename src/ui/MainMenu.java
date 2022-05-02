package ui;

import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;
import model.RoomType;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

public class MainMenu {
    private static final HotelResource hotelResource = new HotelResource();
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat ("yyyy-MM-dd");
    private static final Scanner scanner = new Scanner(System.in);
    private boolean needExit;
    private boolean needSwitchMenu;

    public MainMenu() {
        initMenu();
    }
    private void initMenu() {
        this.needExit = false;
        this.needSwitchMenu = false;
    }
    public void display() {
        System.out.println("\nWelcome to the Hotel Reservation Application\n");
        System.out.println("==============================================");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
        System.out.println("==============================================");
    }
    public void handleUserInput() {
        initMenu();
        System.out.println("Please select a number for the menu option");
        System.out.print(">> ");
        String option = scanner.next();
        switch (option) {
            case "1" -> handleEvent1();
            case "2" -> handleEvent2();
            case "3" -> handleEvent3();
            case "4" -> handleEvent4();
            case "5" -> handleEvent5();
            default -> {}
        }
    }
    public void handleEvent1() {
        // 1. Find and reserve a room
        Date checkIn;
        Date checkOut;
        String email;
        String roomNumber;

        // a data structure which stores searching result of available rooms
        HashSet<String> availableRoomNumbers = new HashSet<>();

        while (true) {
            System.out.println("Enter CheckIn Date mm/dd/yyyy, example: 02/01/2022");
            System.out.print(">> ");
            checkIn = InputFormatter.formatDate(scanner.next());
            if (checkIn != null) {
                break;
            }
            System.out.println("Wrong Date format, please enter again.");
        }

        while (true) {
            System.out.println("Enter CheckOut Date mm/dd/yyyy, example: 02/03/2022");
            System.out.print(">> ");
            checkOut = InputFormatter.formatDate(scanner.next());
            if (checkOut != null) {
                break;
            }
            System.out.println("Wrong Date format, please enter again.");
        }

        LinkedList<IRoom> roomsMatched = hotelResource.findARoom(checkIn, checkOut);

        if (!roomsMatched.isEmpty()) {
            System.out.println("The following rooms are available");
            for (IRoom room : roomsMatched) {
                System.out.println(room.toString());
                availableRoomNumbers.add(room.getRoomNumber());
            }
        } else {
            // add 7 days to the date interval
            checkIn = new Date(checkIn.getTime() + 7 * 86400000);
            checkOut = new Date(checkOut.getTime() + 7 * 86400000);
            LinkedList<IRoom> newRoomsMatched = hotelResource.findARoom(checkIn, checkOut);
            if (newRoomsMatched.isEmpty()) {
                System.out.println("Sorry! No available room.");
                return;
            } else {
                System.out.println("Currently no available room for your selected date interval.");
                System.out.println("Please consider " + dateFormatter.format(checkIn) + " to " + dateFormatter.format(checkOut));
                System.out.println("Here are some rooms in the above date interval");
                for (IRoom room: newRoomsMatched) {
                    System.out.println(room.toString());
                    availableRoomNumbers.add(room.getRoomNumber());
                }
            }
        }

        do {
            System.out.println("Would you like to book a room? (y/n)");
            System.out.print(">> ");
            String option = scanner.next();
            if (option.compareTo("n") == 0 || option.compareTo("N") == 0) {
                return;
            } else if (option.compareTo("y") == 0 || option.compareTo("Y") == 0) {
                break;
            }
        } while(true);

        do {
            System.out.println("Do you have an account with us? (y/n)");
            System.out.print(">> ");
            String option = scanner.next();
            if (option.compareTo("n") == 0 || option.compareTo("N") == 0) {
                System.out.println("Please create an account in the main menu first!");
                return;
            } else if (option.compareTo("y") == 0 || option.compareTo("Y") == 0) {
                break;
            }
        } while (true);

        do {
            System.out.println("Please enter your email, example: lucky@gmail.com");
            System.out.print(">> ");
            email = InputFormatter.formatEmail(scanner.next());
            if (email == null) {
                System.out.println("The email is invalid, please enter again.");
                continue;
            }
            if (hotelResource.getCustomer(email) == null) {
                System.out.println("The email is not found, please create an account first.");
                return;
            } else {
                break;
            }
        } while (true);

        do {
            System.out.println("What room number would you like to reserve?");
            System.out.print(">> ");
            roomNumber = InputFormatter.formatRoomNumber(scanner.next());
            if (roomNumber == null) {
                System.out.println("The room number is invalid, please enter again.");
                continue;
            }
            if (!availableRoomNumbers.contains(roomNumber)) {
                System.out.println("The room number does not exist, please enter again.");
            } else {
                break;
            }
        } while (true);

        IRoom roomToBook = hotelResource.getRoom(roomNumber);
        Reservation reservation = hotelResource.bookARoom(email, roomToBook, checkIn, checkOut);

        System.out.println("Success!");
        System.out.println("[ Your Reservation ]");
        System.out.println("Name: " + reservation.customer.firstName + " " + reservation.customer.lastName);
        String roomTypeString;
        if (roomToBook.getRoomType() == RoomType.SINGLE) { roomTypeString = "Single Bed"; }
        else { roomTypeString = "Double Bed"; }
        System.out.println("Room: " + roomToBook.getRoomNumber() + " - " + roomTypeString);
        System.out.println("Price: $" + roomToBook.getRoomPrice().toString() + " per night");
        System.out.println("Checkin Date: " + dateFormatter.format(checkIn));
        System.out.println("Checkout Date: " + dateFormatter.format(checkOut));

    }
    public void handleEvent2() {
        // 2. See my reservations
        String email;

        do {
            System.out.println("Please enter your email, example: lucky@gmail.com");
            System.out.print(">> ");
            email = InputFormatter.formatEmail(scanner.next());
            if (email == null) {
                System.out.println("The email is invalid, please enter again.");
                continue;
            }
            if (hotelResource.getCustomer(email) == null) {
                System.out.println("The email is not found, please create an account first.");
                return;
            } else {
                break;
            }

        } while (true);

        LinkedList<Reservation> customerReservations = hotelResource.getCustomerReservations(email);

        System.out.println("\n[ Your Reservation ]");
        for (Reservation reservation: customerReservations) {

            Customer customer = reservation.customer;
            IRoom room = reservation.room;

            System.out.println("Name: " + customer.firstName + " " + customer.lastName);
            String roomTypeString;
            if (room.getRoomType() == RoomType.SINGLE) { roomTypeString = "Single Bed"; }
            else { roomTypeString = "Double Bed"; }
            System.out.println("Room: " + room.getRoomNumber() + " - " + roomTypeString);
            System.out.println("Price: $" + room.getRoomPrice().toString() + " per night");
            System.out.println("Checkin Date: " + dateFormatter.format(reservation.checkInDate));
            System.out.println("Checkout Date: " + dateFormatter.format(reservation.checkOutDate));
            System.out.print("\n");
        }
    }
    public void handleEvent3() {
        // 3. Create an account
        System.out.println("Please enter your first name.");
        System.out.print(">> ");
        String firstName = scanner.next();
        System.out.println("Please enter your last name.");
        System.out.print(">> ");
        String lastName = scanner.next();

        String email;
        do {
            System.out.println("Please enter your email.");
            System.out.print(">> ");
            email = InputFormatter.formatEmail(scanner.next());
            if (email == null) {
                System.out.println("The email is invalid, please enter again.");
            } else {
                break;
            }
        } while (true);
        hotelResource.createCustomer(email, firstName, lastName);
    }
    public void handleEvent4() {
        // 4. Admin
        this.needSwitchMenu = true;
    }
    public void handleEvent5() {
        // 5. Exit
        this.needExit = true;
    }
    public boolean needToExit() {
        return this.needExit;
    }
    public boolean needToSwitchMenu() {
        return this.needSwitchMenu;
    }
}
