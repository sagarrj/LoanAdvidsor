package com.finance.LoanAdvisor.customer;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.finance.LoanAdvisor.customer.VO.CustomerVO;
import com.finance.LoanAdvisor.entities.Customer;
import com.finance.LoanAdvisor.entities.repository.CustomerRepository;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerServiceTest {

	@MockBean
	CustomerRepository customerRepository;

	@Autowired
	CustomerService customerService;

	private Customer customer;
	private  CustomerVO customerVO;

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
		customerVO = new CustomerVO();
		customerVO.setCustomerId(10);
		customer.setFirstName("Pooja");
		customer.setLastName("Patil");
		customer.setEmail("poojapatil@gmail.com");
		customer.setAge(31);
		customer.setCreditScore(900);
		customer.setIncome(70000);

	}

	@Test
	void testGetAllCustomers() {
		List<Customer> savedCustomerList = new ArrayList<>();
		List<CustomerVO> savedCustomerVOList = new ArrayList<>();
		savedCustomerList.add(customer);
		savedCustomerVOList.add(new CustomerVO(10, "Pooja", "Patil", "poojapatil@gmail.com", 31, 70000, 900));
		Mockito.when(customerRepository.findAllByStatus(STATUS)).thenReturn(savedCustomerList);
		List<CustomerVO> customerVOList = customerService.getAllCustomers();
		Assertions.assertNotNull(customerVOList);
		Assertions.assertEquals(savedCustomerVOList, customerVOList);
	}

	@Test
	void testGetCustomer() {
		CustomerVO savedCustomerVO = new CustomerVO(10, "Pooja", "Patil", "poojapatil@gmail.com", 31, 70000, 900);
		doReturn(Optional.of(customer)).when(customerRepository).findById(10);
		Optional<CustomerVO> customerInfo = Optional.of(customerService.getCustomer(10));
		Assertions.assertTrue(customerInfo.isPresent());
		Assertions.assertEquals(savedCustomerVO, customerInfo.get());
	}

	@Test
	void testAddCustomer() {
		Customer savedCustomer = null;
		CustomerVO savedCustomerVO = new CustomerVO(10, "Pooja", "Patil", "poojapatil@gmail.com", 31, 70000, 900);
		when(customerRepository.findByEmail("poojapatil@gmail.com")).thenReturn(savedCustomer);
		when(customerRepository.save(customer)).thenReturn(customer);
		CustomerVO customerVOInfo = customerService.addCustomer(customer);
		Assertions.assertNotNull(customerVOInfo);
		Assertions.assertEquals(savedCustomerVO, customerVOInfo);
		
	}

}
