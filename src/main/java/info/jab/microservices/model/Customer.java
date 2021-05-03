package info.jab.microservices.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table("CUSTOMER")
public class Customer {

    @Id
    private String CUST_CODE;
    private String CUST_NAME;
    private String CUST_CITY;
    private String WORKING_AREA;
    private String CUST_COUNTRY;
    private int GRADE;
    private int OPENING_AMT;
    private int RECEIVE_AMT;
    private int PAYMENT_AMT;
    private int OUTSTANDING_AMT;
    private String PHONE_NO;
    private String AGENT_CODE;
}
