package com.finance.loanadvisor.entities.repository;

import com.finance.loanadvisor.entities.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.doReturn;

/**
 * @author priypawa This class contains test cases of {@link CustomerRepository}
 *         by mocking it.
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
class CustomerRepositoryTest {

	@MockBean
	CustomerRepository customerRepository;

	private Customer customer;

	private static final int DEFAULT_ID = 0;

	private static final char STATUS = 'A';

	/**
	 * This method will initialize {@link Customer} object to below parameters and
	 * execute before each test case.
	 */
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

	/**
	 * This method test findByEmail method by giving email as input and return
	 * {@link Customer} details. Check assertNotNull and assertSame
	 */
	@Test
	@DisplayName("Test findByEmail")
	void testFindByEmailValid() {
		doReturn(customer).when(customerRepository).findByEmail("poojapatil@gmail.com");
		Customer customerInfo = customerRepository.findByEmail("poojapatil@gmail.com");
		Assertions.assertNotNull(customerInfo);
		Assertions.assertSame(customer, customerInfo);
	}
	/**
	 * This method test findAllByStatus method by giving status as input and return
	 * {@link List} of {@link Customer} details. Check assertNotNull and assertSame
	 */
	@Test
	@DisplayName("Test testFindAllByStatus")
	void testFindAllByStatus() {
		List<Customer> savedCustomerList = new ArrayList<>();
		savedCustomerList.add(customer);
		doReturn(savedCustomerList).when(customerRepository).findAllByStatus(STATUS);
		List<Customer> customerList = customerRepository.findAllByStatus(STATUS);
		Assertions.assertNotNull(customerList);
		Assertions.assertSame(savedCustomerList, customerList);
	}

}
