package info.jab.microservices.service;

import info.jab.microservices.model.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> getCustomers();
    void removeUSACustomers();
    void removeUSACustomersOptimized();

}
