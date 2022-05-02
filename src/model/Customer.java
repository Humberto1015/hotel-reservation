package model;

import java.util.Objects;
import java.util.regex.Pattern;

public class Customer {
    public final String firstName;
    public final String lastName;
    public final String email;

    public Customer(String firstName, String lastName, String email) {

        String regex = "^\\w{1,63}@[a-zA-Z/d]{2,63}\\.[a-zA-Z]{2,63}(\\.[a-zA-Z]{2,63})?$";
        Pattern emailPattern = Pattern.compile(regex);
        boolean isMatch = emailPattern.matcher(email).matches();

        if (!isMatch) {
            throw new IllegalArgumentException();
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(firstName, customer.firstName) && Objects.equals(lastName, customer.lastName) && Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email);
    }

    @Override
    public String toString() {
        return this.firstName + " " + this.lastName + " Email: " + this.email;
    }

}
