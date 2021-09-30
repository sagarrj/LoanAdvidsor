package com.finance.LoanAdvisor.customer;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.finance.LoanAdvisor.config.DataNotFoundException;
import com.finance.LoanAdvisor.customer.dto.CustomerDTO;
import com.finance.LoanAdvisor.entities.Customer;
import com.finance.LoanAdvisor.entities.repository.CustomerRepository;

@SpringBootTest
class CustomerServiceTest {

	private static final String LIST_IS_EMPTY = "List is empty";

	private static final String CUSTOMER_IS_ALREADY_CREATED = "Customer is already created";
	private static final String CUSTOMER_NOT_FOUND = "Customer not found";

	@MockBean
	CustomerRepository customerRepository;

	@Autowired
	CustomerService customerService;

	private Customer customer;
	private  CustomerDTO customerVO;

	private static final int DEFAULT_ID = 0;

	private static final char STATUS = 'A';

	@BeforeEach
	void initCustomerObject() {
		customer = new Customer();
		customer.setCustomerId(10);
		customer.setFirstName("Pooja");
		customer.setLastName("Patil");
		customer.setCity("Pune");
		customer.setGender("Female");
		customer.setEmail("poojapatil@gmail.com");
		customer.setAge(31);
		customer.setPhoneNo("8122459560");
		customer.setIncome(70000);
		customer.setAadharNo("211088234504");
		customer.setPanNo("ZBDKE7723K");
		customer.setCreditScore(900);
		customer.setInitialAmount(30000);
		customer.setLoanRequirement(400000);
		customer.setStatus(STATUS);
		customer.setCreateDttm(new Date());
		customer.setCreatedBy(DEFAULT_ID);
	}

	@BeforeEach
	void initCustomerVO() {
		customerVO = new CustomerDTO();
		customerVO.setCustomerId(10);
		customerVO.setFirstName("Pooja");
		customerVO.setLastName("Patil");
		customerVO.setEmail("poojapatil@gmail.com");
		customerVO.setAge(31);
		customerVO.setCreditScore(900);
		customerVO.setIncome(70000);

	}

	@Test
	@DisplayName("Test Customer List --Success")
	void testGetAllCustomersValid() {
		List<Customer> savedCustomerList = new ArrayList<>();
		List<CustomerDTO> savedCustomerVOList = new ArrayList<>();
		savedCustomerList.add(customer);
		savedCustomerVOList.add(customerVO);
		Mockito.when(customerRepository.findAllByStatus(STATUS)).thenReturn(savedCustomerList);
		List<CustomerDTO> customerVOList = customerService.getAllCustomers();
		Assertions.assertNotNull(customerVOList);
		Assertions.assertEquals(savedCustomerVOList, customerVOList);
	}
	
	@Test
	@DisplayName("Test Customer List --Not Found")
	void testGetAllCustomersInvalid() {
		List<Customer> savedCustomerList = new ArrayList<>();
		Mockito.when(customerRepository.findAllByStatus(STATUS)).thenReturn(savedCustomerList);
		Throwable exception = assertThrows(DataNotFoundException.class,()->{
	    customerService.getAllCustomers();

		});
		
		Assertions.assertEquals(LIST_IS_EMPTY, exception.getMessage());

	}

	@Test
	@DisplayName("Test Get Customer --Success")
	void testGetCustomerValid() {
		doReturn(Optional.of(customer)).when(customerRepository).findById(10);
		Optional<CustomerDTO> customerInfo = Optional.of(customerService.getCustomer(10));
		Assertions.assertTrue(customerInfo.isPresent());
		Assertions.assertEquals(customerVO, customerInfo.get());
	}
	
	@Test
	@DisplayName("Test Get Customer --Not Found")
	void testGetCustomerInvalid() throws DataNotFoundException {
		  doReturn(Optional.empty()).when(customerRepository).findById(10);
		  Throwable exception = assertThrows(DataNotFoundException.class,()->{
			  Optional.of(customerService.getCustomer(10));
                
			});
		  Assertions.assertEquals(CUSTOMER_NOT_FOUND, exception.getMessage());
		

	}


	@Test
	@DisplayName("Test Add Customer --Success")
	void testAddCustomerValid() {
		Customer savedCustomer = null;
		when(customerRepository.findByEmail("poojapatil@gmail.com")).thenReturn(savedCustomer);
		when(customerRepository.save(customer)).thenReturn(customer);
		CustomerDTO customerVOInfo = customerService.addCustomer(customer);
		Assertions.assertNotNull(customerVOInfo);
		Assertions.assertEquals(customerVO, customerVOInfo);
		
	}
	
	@Test
	@DisplayName("Test Add Customer --Not Found")
	void testAddCustomerInvalid() throws DataNotFoundException{
		when(customerRepository.findByEmail("poojapatil@gmail.com")).thenReturn(customer);
		when(customerRepository.save(customer)).thenReturn(customer);
		  Throwable exception = assertThrows(DataNotFoundException.class,()->{
		   customerService.addCustomer(customer);
		  });
		  Assertions.assertEquals(CUSTOMER_IS_ALREADY_CREATED, exception.getMessage());
	}

}
