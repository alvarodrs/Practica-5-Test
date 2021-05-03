package info.jab.microservices.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.jab.microservices.model.Customer;
import info.jab.microservices.service.CustomerService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService service;

    @Test
    public void given_controller_when_getCustomers_then_Ok() throws Exception {

        //Given
        Customer expectedCustomer = getCustomer();
        when(service.getCustomers()).thenReturn(List.of(expectedCustomer));

        //When
        //Then
        this.mockMvc
                .perform(get("/api/customers").contentType(APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(asJsonString(expectedCustomer))));

    }

    private Customer getCustomer() {

        return Customer.builder()
                .CUST_CODE("034")
                .CUST_NAME("John")
                .CUST_COUNTRY("Spain")
                .CUST_CITY("Madrid")
                .WORKING_AREA("Madrid")
                .GRADE(3)
                .OPENING_AMT(1000)
                .RECEIVE_AMT(1000)
                .PAYMENT_AMT(1000)
                .OUTSTANDING_AMT(1000)
                .PHONE_NO("655123456")
                .AGENT_CODE("123")
                .build();
    }

    public static String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

