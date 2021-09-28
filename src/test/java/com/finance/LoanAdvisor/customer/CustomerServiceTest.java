package com.finance.LoanAdvisor.customer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.finance.LoanAdvisor.entities.repository.CustomerRepository;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerServiceTest {

	@MockBean
	CustomerRepository customerRepository;
	
	@Autowired
	CustomerService customerService;
	
	@Test
	void testGetAllCustomers() {
		
	}
	
	@Test
	void testGetCustomer() {
		fail("Not yet implemented");

	}
	
	@Test
	void testAddCustomer() {
		fail("Not yet implemented");
	}

}
