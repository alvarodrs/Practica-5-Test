package info.jab.microservices.service;


import info.jab.microservices.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.BDDAssertions.then;

@Slf4j
@SpringBootTest
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Test
    @Sql(scripts= "/sql-data.sql")
    @Transactional
    public void given_repository_when_delete_customers_from_USA_then_Ok() {

        //Given
        List<Customer> allCustomers = customerService.getCustomers();
        List<Customer> allUSACustomers = allCustomers.stream()
                .filter(customer -> customer.getCUST_COUNTRY().equals("USA"))
                .collect(Collectors.toUnmodifiableList());

        //When
        customerService.removeUSACustomers();

        //Then
        List<Customer> allCustomers2 = customerService.getCustomers();
        then(allCustomers2.size()).isEqualTo(allCustomers.size() - allUSACustomers.size());
    }


    @Test
    @Sql(scripts= "/sql-data.sql")
    @Transactional
    public void given_repository_when_delete_customers_from_USA_Optimized_then_Ok() {


        //Given
        List<Customer> allCustomers = customerService.getCustomers();
        List<Customer> allUSACustomers = allCustomers.stream()
                .filter(customer -> customer.getCUST_COUNTRY().equals("USA"))
                .collect(Collectors.toUnmodifiableList());

        //When
        customerService.removeUSACustomersOptimized();

        //Then
        List<Customer> allCustomers2 = customerService.getCustomers();
        then(allCustomers2.size()).isEqualTo(allCustomers.size() - allUSACustomers.size());

    }

}