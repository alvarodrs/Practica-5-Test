package info.jab.microservices.service.impl;

import info.jab.microservices.model.Customer;
import info.jab.microservices.repository.CustomerRepository;
import info.jab.microservices.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Customer> getCustomers() {
        return StreamSupport.stream(customerRepository.findAll().spliterator(), false)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void removeUSACustomers() {
        StreamSupport.stream(customerRepository.findAll().spliterator(), false)
                .filter(customer -> customer.getCUST_COUNTRY().equals("USA"))
                .map(Customer::getCUST_CODE)
                .forEach(id -> customerRepository.deleteById(id));
    }

    @Override
    public void removeUSACustomersOptimized() {
        String sql = "DELETE FROM CUSTOMER WHERE CUST_COUNTRY='USA'";
        jdbcTemplate.execute(sql);
    }
}
