package com.finance.loanadvisor.customer;

import com.finance.loanadvisor.Sanction.dto.SanctionDTO;


import com.finance.loanadvisor.customer.dto.CustomerDTO;
import com.finance.loanadvisor.entities.Customer;
import com.finance.loanadvisor.entities.Loan;
import com.finance.loanadvisor.entities.LoanType;
import com.finance.loanadvisor.entities.Sanction;
import com.finance.loanadvisor.entities.repository.CustomerRepository;
import com.finance.loanadvisor.entities.repository.LoanRepository;
import com.finance.loanadvisor.entities.repository.SanctionRepository;
import com.finance.loanadvisor.exception.ApplicationException;
import com.finance.loanadvisor.exception.DataNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

/**
 * @author priypawa This class includes all test cases of
 *         {@link CustomerService} by mocking {@link CustomerRepository},
 *         {@link LoanRepository}, {@link SanctionRepository}
 *
 */
@SpringBootTest
class CustomerServiceTest {

	private static final String LIST_IS_EMPTY = "List is empty";

	private static final String CUSTOMER_IS_ALREADY_CREATED = "Customer is already created";
	private static final String CUSTOMER_NOT_FOUND = "Customer not found";
    private static final String LOAN_NOT_FOUND= "Loan Details not found";
	@MockBean
	CustomerRepository customerRepository;

	@MockBean
	LoanRepository loanRepository;

	@MockBean
	SanctionRepository sanctionRepository;

	@Autowired
	CustomerService customerService;

	private Customer customer;

	private CustomerDTO customerDTO;

	private com.finance.loanadvisor.Sanction.dto.SanctionDTO sanctionDTO;
	private Loan loan;

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
	 * This method will initialize {@link CustomerDTO} object to below parameters
	 * and execute before each test case.
	 */
	@BeforeEach
	void initCustomerDTO() {
		customerDTO = new CustomerDTO();
		customerDTO.setCustomerId(10);
		customerDTO.setFirstName("Pooja");
		customerDTO.setLastName("Patil");
		customerDTO.setEmail("poojapatil@gmail.com");
		customerDTO.setAge(31);
		customerDTO.setCreditScore(900);
		customerDTO.setIncome(70000);

	}

	/**
	 * This method will initialize {@link LoanType} object to below parameters and
	 * execute before each test case.
	 */
	@BeforeEach
	void initLoan() {
		LoanType loanType = new LoanType();
		loanType.setLoanDescription("EDUCATIONAL");
		loan = new Loan();
		loan.setLoanId(5);
		loan.setROI(8.50);
		loan.setLoanType(loanType);

	}

	/**
	 * This method will initialize {@link SanctionDTO} object to below parameters
	 * and execute before each test case.
	 */
	@BeforeEach
	void initSanctionDTO() {
		sanctionDTO = new SanctionDTO();
		sanctionDTO.setRoi(8.50);
		sanctionDTO.setLoanAmount(30000.0);
		sanctionDTO.setLoanType("EDUCATIONAL");

	}

	/**
	 * This method test getAllCustomer of {@link CustomerService} and return
	 * {@link List} of {@link CustomerDTO} based on status Checks assertNotNull and
	 * assertEquals methods.
	 */
	@Test
	@DisplayName("Test Customer List --Success")
	void testGetAllCustomersValid() {
		List<Customer> savedCustomerList = new ArrayList<>();
		List<CustomerDTO> savedCustomerDTOList = new ArrayList<>();
		savedCustomerList.add(customer);
		savedCustomerDTOList.add(customerDTO);
		Mockito.when(customerRepository.findAllByStatus(STATUS)).thenReturn(savedCustomerList);
		List<CustomerDTO> customerDTOList = customerService.getAllCustomers();
		Assertions.assertNotNull(customerDTOList);
		Assertions.assertEquals(savedCustomerDTOList, customerDTOList);
	}

	/**
	 * This method test getAllCustomer of {@link CustomerService} by mocking empty
	 * {@link List} of {@link CustomerDTO} based on status. Checks assertEquals
	 * methods.
	 *
	 * @throws ApplicationException with error message.
	 */
	@Test
	@DisplayName("Test Customer List --Not Found")
	void testGetAllCustomersInvalid() {
		List<Customer> savedCustomerList = new ArrayList<>();
		Mockito.when(customerRepository.findAllByStatus(STATUS)).thenReturn(savedCustomerList);
		Throwable exception = assertThrows(ApplicationException.class, () -> {
			customerService.getAllCustomers();

		});

		Assertions.assertEquals(LIST_IS_EMPTY, exception.getMessage());

	}

	/**
	 * This method test getCustomer of {@link CustomerService} by taking input as
	 * customerId and return {@link CustomerDTO} object. Checks assertTrue and
	 * assertEquals methods.
	 */
	@Test
	@DisplayName("Test Get Customer --Success")
	void testGetCustomerValid() {
		doReturn(Optional.of(customer)).when(customerRepository).findById(10);
		Optional<CustomerDTO> customerInfo = Optional.of(customerService.getCustomer(10));
		Assertions.assertTrue(customerInfo.isPresent());
		Assertions.assertEquals(customerDTO, customerInfo.get());
	}

	/**
	 * This method test getCustomer of {@link CustomerService} by taking input as
	 * customerId and return empty {@link CustomerDTO}. Checks assertEquals methods.
	 *
	 * @throws ApplicationException with error message.
	 */
	@Test
	@DisplayName("Test Get Customer --Not Found")
	void testGetCustomerInvalid() {
		doReturn(Optional.empty()).when(customerRepository).findById(10);
		Throwable exception = assertThrows(ApplicationException.class, () -> {
			Optional.of(customerService.getCustomer(10));

		});
		Assertions.assertEquals(CUSTOMER_NOT_FOUND, exception.getMessage());

	}

	/**
	 * This method test addCustomer of {@link CustomerService} by taking input as
	 * {@link Customer} and return {@link CustomerDTO} object. It mocks findByEmail
	 * method of repository and empty {@link Customer} object. Checks assertNotNull
	 * method.
	 */
	@Test
	@DisplayName("Test Add Customer --Success")
	void testAddCustomerValid() {
		Customer savedCustomer = null;
		when(customerRepository.findByEmail("poojapatil@gmail.com")).thenReturn(savedCustomer);
		when(customerRepository.save(customer)).thenReturn(customer);
		CustomerDTO customerDTOInfo = customerService.addCustomer(customer);
		Assertions.assertNotNull(customerDTOInfo);

	}

	/**
	 * This method test addCustomer of {@link CustomerService} by taking input as
	 * {@link Customer} and return {@link CustomerDTO} object. It mocks findByEmail
	 * method of repository and {@link Customer} object. Checks assertEquals
	 * methods.
	 *
	 * @throws ApplicationException with error message.
	 */
	@Test
	@DisplayName("Test Add Customer --Not Found")
	void testAddCustomerInvalid() {
		when(customerRepository.findByEmail("poojapatil@gmail.com")).thenReturn(customer);
		when(customerRepository.save(customer)).thenReturn(customer);
		Throwable exception = assertThrows(ApplicationException.class, () -> {
			customerService.addCustomer(customer);
		});
		Assertions.assertEquals(CUSTOMER_IS_ALREADY_CREATED, exception.getMessage());
	}

	@Test
	@DisplayName("TEST GET Customer after Sanction ")
	void testCustomerLoanEligibilityValid(){
		customer.setLoanRequirement(30000);
		customer.setGender("Male");
		Sanction sanction = new Sanction(1, customer, loan, 30000.0, 8.50, 'A', null, null, null, null);
		when(customerRepository.findById(10)).thenReturn(Optional.of(customer));
		when(loanRepository.findById(5)).thenReturn(Optional.of(loan));
		when(sanctionRepository.save(sanction)).thenReturn(sanction);
		SanctionDTO sanctionDTO = customerService.customerLoanEligibility(10,5);
        Assertions.assertNotNull(customer);
		Assertions.assertNotNull(loan);
		Assertions.assertEquals(30000.0, sanctionDTO.getLoanAmount());
	}

	@Test
	@DisplayName("TEST GET Customer --NOT FOUND")
	void testCustomerEligibilityInvalid() throws ApplicationException {
		doReturn(Optional.empty()).when(customerRepository).findById(10);
		Throwable exception = assertThrows(ApplicationException.class,()->{
			Optional.of(customerService.customerLoanEligibility(10,5));
		});
		Assertions.assertEquals(CUSTOMER_NOT_FOUND, exception.getMessage());

	}

	@Test
	@DisplayName("TEST GET Loan--NOT FOUND")
	void testCustomerLoanEligibilityInvalid() throws ApplicationException {
		when(customerRepository.findById(10)).thenReturn(Optional.of(customer));
		doReturn(Optional.empty()).when(loanRepository).findById(5);
		Throwable exception = assertThrows(ApplicationException.class,()->{
			Optional.of(customerService.customerLoanEligibility(10,5));
		});
		Assertions.assertEquals(LOAN_NOT_FOUND, exception.getMessage());

	}
}
