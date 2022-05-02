package model;

import java.util.Objects;

public class Room implements IRoom {
    protected String roomNumber;
    protected Double price;
    protected RoomType roomType;

    public Room(String roomNumber, Double price, RoomType roomType) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.roomType = roomType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(roomNumber, room.roomNumber) && Objects.equals(price, room.price) && roomType == room.roomType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, price, roomType);
    }

    @Override
    public String getRoomNumber() {
        return this.roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return this.price;
    }

    @Override
    public RoomType getRoomType() {
        return this.roomType;
    }

    @Override
    public boolean isFree() {
        return this.price == 0;
    }

    @Override
    public String toString() {
        String roomTypeString;
        if (this.roomType == RoomType.SINGLE) roomTypeString = "Single Bed Room";
        else roomTypeString = "Double Bed Room";

        String priceInString;
        if (isFree()) {
            priceInString = "Free";
        } else {
            priceInString = "$" + this.price.toString();
        }

        return "Room Number: " + this.roomNumber + " Type: " + roomTypeString + " Price: " + priceInString;
    }
}
