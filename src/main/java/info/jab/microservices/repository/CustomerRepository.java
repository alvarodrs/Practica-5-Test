package info.jab.microservices.repository;

import info.jab.microservices.model.Customer;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, String> {

    @Query("SELECT * FROM CUSTOMER")
    List<Customer> baseMyQuery();

    @Query("SELECT * FROM CUSTOMER WHERE CUST_NAME LIKE 'S%'")
    List<Customer> myQuery();

    @Query("SELECT * FROM CUSTOMER WHERE CUST_COUNTRY = 'Canada'")
    List<Customer> canadaQuery();

    @Query("SELECT MAX(PAYMENT_AMT) FROM CUSTOMER WHERE CUST_COUNTRY = 'UK'")
    int paymentQuery();

}
