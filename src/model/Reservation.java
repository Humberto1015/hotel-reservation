package model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Reservation {
    public final Customer customer;
    public final IRoom room;
    public final Date checkInDate;
    public final Date checkOutDate;


    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(customer, that.customer) && Objects.equals(room, that.room) && Objects.equals(checkInDate, that.checkInDate) && Objects.equals(checkOutDate, that.checkOutDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, room, checkInDate, checkOutDate);
    }

    @Override
    public String toString() {
        String customerName = this.customer.firstName + " " + this.customer.lastName;
        SimpleDateFormat dateFormatter = new SimpleDateFormat ("yyyy-MM-dd");
        String checkInDateString = dateFormatter.format(this.checkInDate);
        String checkOutDateString = dateFormatter.format(this.checkOutDate);
        return "Customer: " + customerName + " Checkin Date: " + checkInDateString + " Checkout Date: " + checkOutDateString;
    }
}
