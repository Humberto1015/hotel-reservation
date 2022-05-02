package service;

import model.Customer;
import java.util.LinkedList;

public final class CustomerService {

    private static CustomerService INSTANCE;
    private CustomerService() {
    }
    public static CustomerService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CustomerService();
        }
        return INSTANCE;
    }
    private static final LinkedList<Customer> customers = new LinkedList<>();
    public void addCustomer(String email, String firstName, String lastName) {
        customers.add(new Customer(firstName, lastName, email));
    }
    public Customer getCustomer(String email) {
        for (Customer customer: customers) {
            if (customer.email.compareTo(email) == 0) {
                return customer;
            }
        }
        return null;
    }

    public LinkedList<Customer> getAllCustomers() {
        return customers;
    }
}
