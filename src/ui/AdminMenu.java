package ui;

import api.AdminResource;
import model.*;

import java.util.LinkedList;
import java.util.Scanner;

public class AdminMenu {
    private static final AdminResource adminResource = new AdminResource();
    private static final Scanner scanner = new Scanner(System.in);
    private boolean needSwitchMenu;
    public AdminMenu() {
        initMenu();
    }
    private void initMenu() {
        this.needSwitchMenu = false;
    }
    public void display() {
        System.out.println("\n======== Admin Menu ========");
        System.out.println("1. See all Customers");
        System.out.println("2. See all Rooms");
        System.out.println("3. See all Reservations");
        System.out.println("4. Add a Room");
        System.out.println("5. Back to Main Menu");
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
    private void handleEvent1() {
        // 1. See all Customers
        LinkedList<Customer> customers = adminResource.getAllCustomers();

        System.out.println("[ Customer List ]");
        if (customers.isEmpty()) {
            System.out.println("< No Customer >");
            return;
        }
        for (Customer customer: customers) {
            System.out.println(customer.toString());
        }
    }
    private void handleEvent2() {
        // 2. See all Rooms
        LinkedList<IRoom> rooms = adminResource.getAllRooms();

        System.out.println("[ Room List ]");
        if (rooms.isEmpty()) {
            System.out.println("< No Room >");
            return;
        }
        for (IRoom room : rooms) {
            System.out.println(room.toString());
        }
    }
    private void handleEvent3() {
        // 3. See all Reservations
        adminResource.displayAllReservations();
    }
    private void handleEvent4() {
        // 4. Add a Room
        LinkedList<IRoom> roomsToAdd = new LinkedList<>();
        boolean needAddAnotherRoom;
        do {

            String roomNumber;
            Double price;
            RoomType roomType;

            do {
                System.out.println("Enter room number");
                System.out.print(">> ");
                roomNumber = InputFormatter.formatRoomNumber(scanner.next());

                if (roomNumber == null) {
                    System.out.println("The room number is invalid, please enter again.");
                    continue;
                }
                if (adminResource.roomNumberDoesExist(roomNumber)) {
                    System.out.println("Sorry! This room number already exists!");
                } else {
                    adminResource.recordRoomNumber(roomNumber);
                    break;
                }

            } while (true);

            do {
                System.out.println("Enter price per night");
                System.out.print(">> ");
                price = InputFormatter.formatPrice(scanner.next());
                if (price == null) {
                    System.out.println("The price is invalid, please enter again.");
                } else {
                    break;
                }
            } while (true);

            do {
                System.out.println("Enter room type: 1 for single bed, 2 for double bed");
                System.out.print(">> ");
                roomType = InputFormatter.formatRoomType(scanner.next());
                if (roomType == null) {
                    System.out.println("The room type is invalid, please enter again.");
                } else {
                    break;
                }
            } while (true);

            if (price == 0) {
                roomsToAdd.add(new FreeRoom(roomNumber, price, roomType));
            } else {
                roomsToAdd.add(new Room(roomNumber, price, roomType));
            }

            do {
                System.out.println("Want to add another room? (y/n)");
                System.out.print(">> ");
                String option = scanner.next();
                if (option.compareTo("n") == 0 || option.compareTo("N") == 0) {
                    needAddAnotherRoom = false;
                    break;
                } else if (option.compareTo("y") == 0 || option.compareTo("Y") == 0) {
                    needAddAnotherRoom = true;
                    break;
                }
            } while (true);

        } while (needAddAnotherRoom);

        adminResource.addRoom(roomsToAdd);
    }
    private void handleEvent5() {
        // 5. Back to Main Menu
        this.needSwitchMenu = true;
    }
    public boolean needToSwitchMenu() {
        return this.needSwitchMenu;
    }
}