package info.jab.microservices.repository;

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
import java.util.stream.StreamSupport;

import static org.assertj.core.api.BDDAssertions.then;

@Slf4j
@SpringBootTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @Sql(scripts= "/sql-data.sql")
    @Transactional
    public void given_repository_when_get_all_customers_then_Ok() {

        //Given
        //When
        List<String> list = StreamSupport.stream(customerRepository.findAll().spliterator(), false)
                .map(Customer::toString)
                .peek(LOGGER::info)
                .collect(Collectors.toUnmodifiableList());

        //Then
        then(list.size()).isEqualTo(25);
    }

    @Test
    @Sql(scripts= "/sql-data.sql")
    @Transactional
    public void given_repository_when_get_all_customers_alternative_then_Ok() {

        //Given
        //When
        List<String> list = StreamSupport.stream(customerRepository.baseMyQuery().spliterator(), false)
                .map(Customer::toString)
                .peek(LOGGER::info)
                .collect(Collectors.toUnmodifiableList());

        //Then
        then(list.size()).isEqualTo(25);
    }

    @Test
    @Sql(scripts= "/sql-data.sql")
    @Transactional
    public void given_repository_when_search_customers_starting_letter_S_then_Ok() {

        //Given
        //When
        Long count = StreamSupport.stream(customerRepository.findAll().spliterator(), false)
                .map(Customer::getCUST_NAME)
                .peek(LOGGER::info)
                .filter(name -> name.startsWith("S"))
                .count();

        //Then
        then(count).isEqualTo(6);
    }

    @Test
    @Sql(scripts= "/sql-data.sql")
    @Transactional
    public void given_repository_when_search_customers_starting_letter_S_Optimized_then_Ok() {

        //Given
        //When
        Long count = StreamSupport.stream(customerRepository.myQuery().spliterator(), false)
                .map(Customer::getCUST_NAME)
                .count();

        //Then
        then(count).isEqualTo(6);
    }

    @Test
    @Sql(scripts= "/sql-data.sql")
    @Transactional
    public void given_repository_when_search_customers_from_Canada_then_Ok() {

        //Given

        //When
        List<String> list = StreamSupport.stream(customerRepository.findAll().spliterator(), false)
                .map(Customer::getCUST_COUNTRY)
                .filter(country -> country.equals("Canada"))
                .peek(LOGGER::info)
                .collect(Collectors.toUnmodifiableList());

        //Then
        then(list.size()).isEqualTo(3);
    }

    @Test
    @Sql(scripts= "/sql-data.sql")
    @Transactional
    public void given_repository_when_search_customers_from_Canada_Optimized_then_Ok() {

        //Given
        //When
        Long count = StreamSupport.stream(customerRepository.canadaQuery().spliterator(), false)
                .map(Customer::getCUST_NAME)
                .count();

        //Then
        then(count).isEqualTo(3);
        //si no hay 3 salta el error
    }

    @Test
    @Sql(scripts= "/sql-data.sql")
    @Transactional
    public void given_repository_when_search_max_payment_from_Uk_customers_then_Ok() {

        //Given

        //When
        Long max = StreamSupport.stream(customerRepository.findAll().spliterator(), false)
                .filter(customer -> customer.getCUST_COUNTRY().equals("UK"))
                .map(Customer::getPAYMENT_AMT)
                .mapToLong(v -> v)
                .max()
                .getAsLong();

        //Then
        then(max).isEqualTo(7000);
    }


    @Test
    @Sql(scripts= "/sql-data.sql")
    @Transactional
    public void given_repository_when_search_max_payment_from_Uk_customers_Optimized_then_Ok() {

        //Given
        //When
        int max_payment = customerRepository.paymentQuery();

        //Then
        then(max_payment).isEqualTo(7000);
    }

}