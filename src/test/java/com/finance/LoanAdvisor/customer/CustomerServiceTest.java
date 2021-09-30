package com.finance.LoanAdvisor.customer;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.finance.LoanAdvisor.Sanction.dto.SanctionDTO;
import com.finance.LoanAdvisor.entities.Loan;
import com.finance.LoanAdvisor.entities.Sanction;
import com.finance.LoanAdvisor.entities.repository.LoanRepository;
import com.finance.LoanAdvisor.entities.repository.SanctionRepository;
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

	@MockBean
	LoanRepository loanRepository;

	@MockBean
	SanctionRepository sanctionRepository;

	@Autowired
	CustomerService customerService;

	private Customer customer;
	private  CustomerVO customerVO;
	private SanctionDTO sanctionDTO;
	private Loan loan;

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
//	@BeforeEach
//	void initCustomer(){
//		customer.setCustomerId(1);
//		customer.setIncome(30000);
//		customer.setCreditScore(700);
//		customer.setAge(30);
//	}

	@BeforeEach
	void initLoan(){
		loan = new Loan();
        loan.setLoanId(5);
		loan.setROI(8.50);
		loan.setLoanDesc("EDUCATIONAL");

	}

	@BeforeEach
	void initSanctionDTO(){
		sanctionDTO=new SanctionDTO();
		sanctionDTO.setRoi(8.50);
		sanctionDTO.setLoanAmount(30000.0);
		sanctionDTO.setLoanType("EDUCATIONAL");

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
	@Test
	void testCustomerLoanEligibility(){
//		Customer customer = new Customer();
		//when(customerRepository.findById(10)).thenReturn(customerVO);
		Sanction sanction = new Sanction(1,customer,loan,30000.0,8.50,'A',null,null,null,null);
//		doReturn(customer).when(customerRepository.findById(10));
//		doReturn(loan).when(loanRepository.findById(5));
//		doReturn(sanctionDTO).when(customerService.convertTOSanctionVO(sanction));
//		doReturn(sanction).when(sanctionRepository.save(sanction));

		when(customerRepository.findById(10)).thenReturn(Optional.of(customer));
		when(loanRepository.findById(5)).thenReturn(Optional.of(loan));
	     when(customerService.convertTOSanctionVO(sanction)).thenReturn(sanctionDTO);
		when(sanctionRepository.save(sanction)).thenReturn(sanction);

		when(customerRepository.save(customer)).thenReturn(customer);
		SanctionDTO sanctionDTO = customerService.customerLoanEligibility(10,5);
        Assertions.assertNotNull(customer);
		Assertions.assertNotNull(loan);
		Assertions.assertEquals(30000.0,sanctionDTO.getLoanAmount());
	}

}
