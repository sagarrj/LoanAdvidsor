package com.finance.loanadvisor.customer;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finance.loanadvisor.Sanction.dto.SanctionDTO;
import com.finance.loanadvisor.customer.dto.CustomerDTO;
import com.finance.loanadvisor.entities.Customer;
import com.finance.loanadvisor.entities.Loan;
import com.finance.loanadvisor.entities.LoanType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author priypawa This class includes all test cases of {@link CustomerController} by
 *         mocking {@link CustomerService} class.
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username="admin")
class CustomerControllerTest {

	@MockBean
	CustomerService customerService;

	@Autowired
	private MockMvc mockMvc;
	private Customer customer;
	private CustomerDTO customerVO;
	private Loan loan;
	private SanctionDTO sanctionDTO;

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
		customerDTO.setIncome(70000);

	}

	@BeforeEach
	void initLoan(){
		LoanType loanType= new LoanType();
		loanType.setLoanDescription("EDUCATIONAL");
		loan = new Loan();
		loan.setLoanId(5);
		loan.setROI(8.50);
		loan.setLoanType(loanType);

	}

	@BeforeEach
	void initSanctionDTO(){
		sanctionDTO=new SanctionDTO();
		sanctionDTO.setRoi(8.50);
		sanctionDTO.setLoanAmount(30000.0);
		sanctionDTO.setLoanType("EDUCATIONAL");

	}



	/**
	 * This method tests status, mediaType and {@link List} of {@link Customer} of
	 * getAllCustomer method {@link CustomerController} by mocking
	 * {@link CustomerService}
	 * 
	 * @throws Exception
	 */
	@Test
	@DisplayName("Test GET /list --Success")
	void testgetAllcustomersValid() throws Exception {
		List<CustomerDTO> savedCustomerDTOList = new ArrayList<>();
		savedCustomerDTOList.add(customerDTO);
		doReturn(savedCustomerDTOList).when(customerService).getAllCustomers();
		mockMvc.perform(get("/customer/list"))
				// Validate status and mediaType
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
		Assertions.assertFalse(savedCustomerDTOList.isEmpty());
	}

	/**
	 * This method tests status and {@link List} of {@link Customer} of
	 * getAllCustomer method {@link CustomerController} and return empty list which
	 * is giving status {@link NotFound}
	 *
	 * @throws Exception
	 */
	@Test
	@DisplayName("Test GET /list --Not Found")
	void testgetAllcustomersInvalid() throws Exception {
		List<CustomerDTO> savedCustomerDTOList = new ArrayList<>();
		doReturn(savedCustomerDTOList).when(customerService).getAllCustomers();
		mockMvc.perform(get("/customer/list"))
				// Validate status
				.andExpect(status().isNotFound());
		Assertions.assertTrue(savedCustomerDTOList.isEmpty());

	}

	@Test
	@DisplayName("Test GET /view --Success")
	void testGetCustomerValid() throws Exception {
		doReturn(customerVO).when(customerService).getCustomer(10);
		mockMvc.perform(get("/customer/view/{id}", 10))
				// Validate status and mediaType
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("customerId", is(10))).andExpect(jsonPath("firstName", is("Pooja")))
				.andExpect(jsonPath("lastName", is("Patil"))).andExpect(jsonPath("email", is("poojapatil@gmail.com")))
				.andExpect(jsonPath("age", is(31))).andExpect(jsonPath("income", is(70000)))
				.andExpect(jsonPath("creditScore", is(900)));
		 Assertions.assertNotNull(customerVO);

	}
	
	@Test
	@DisplayName("Test GET /view --Not Found")
	void testGetCustomerInvalid() throws Exception {
		CustomerDTO customerVO = new CustomerDTO();
		doReturn(customerVO).when(customerService).getCustomer(10);
		 mockMvc.perform(get("/customer/view/{id}",1))
	        .andExpect(status().isNotFound());

	}



	@Test
	@DisplayName("Test POST /add --Success")
	void testAddCustomerValid() throws Exception {
		Customer savedCustomer = new Customer(10, "Pooja", "Patil", "Pune", "Female", "poojapatil@gmail.com", 31,
				"8122459560", 70000, "211088234504", "ZBDKE7723K", 30000, 400000);
		doReturn(customerDTO).when(customerService).addCustomer((ArgumentMatchers.any()));
		mockMvc.perform(
				post("/customer/add").contentType(MediaType.APPLICATION_JSON).content(asJsonString(savedCustomer)))
				.andDo(print())
				// Validate status and mediaType
				.andExpect(status().is(201)).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("customerId", is(10))).andExpect(jsonPath("firstName", is("Pooja")))
				.andExpect(jsonPath("lastName", is("Patil"))).andExpect(jsonPath("email", is("poojapatil@gmail.com")))
				.andExpect(jsonPath("age", is(31))).andExpect(jsonPath("income", is(70000)));
		Assertions.assertNotNull(customerDTO);
	}

	/**
	 * This method tests addCustomer method by accepting empty {@link Customer}
	 * object which is why it's giving {@link BadRequest} status.
	 *
	 * @throws Exception
	 */
	@Test
	@DisplayName("Test POST /add --Not Found")
	void testAddCustomerInvalid() throws Exception {
		CustomerDTO savedCustomerVO = new CustomerDTO();
		mockMvc.perform(
				post("/customer/add").contentType(MediaType.APPLICATION_JSON).content(asJsonString(savedCustomerVO)))
				.andExpect(status().isBadRequest());
	}

	/**
	 * This method tests getCustomer method by accepting customerId and return
	 * {@link CustomerDTO} object. Checks status and media type of response.
	 *
	 * @throws Exception
	 */
	@Test
	@DisplayName("Test GET /view --Success")
	void testGetCustomerValid() throws Exception {
		doReturn(customerDTO).when(customerService).getCustomer(10);
		mockMvc.perform(get("/customer/view/{id}", 10))
				// Validate status and mediaType
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("customerId", is(10))).andExpect(jsonPath("firstName", is("Pooja")))
				.andExpect(jsonPath("lastName", is("Patil"))).andExpect(jsonPath("email", is("poojapatil@gmail.com")))
				.andExpect(jsonPath("age", is(31))).andExpect(jsonPath("income", is(70000)));
		Assertions.assertNotNull(customerDTO);

	}

	/**
	 * This method tests getCustomer method by accepting customerId and return empty
	 * {@link CustomerDTO} object. Checks status as {@link NotFound}
	 *
	 * @throws Exception
	 */
	@Test
	@DisplayName("Test GET /view --Not Found")
	void testGetCustomerInvalid() throws Exception {
		CustomerDTO customerDTO = new CustomerDTO();
		doReturn(customerDTO).when(customerService).getCustomer(10);
		mockMvc.perform(get("/customer/view/{id}", 1)).andDo(print()).andExpect(status().isNotFound());

	}

	@Test
	@DisplayName("Test GET/--Sanction")
	void testGetLoanEligibilityValid() throws Exception {

		doReturn(sanctionDTO).when(customerService).customerLoanEligibility(10,5);
		String url = "/customer/sanction/10/5";
        mockMvc.perform(get(url))
				//validate status and media type
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("roi", is(8.50))).andExpect(jsonPath("loanAmount",is(30000.0)))
				.andExpect(jsonPath("loanType",is("EDUCATIONAL")));
		Assertions.assertNotNull(sanctionDTO);

	}

	@Test
	@DisplayName("Test GET/--Sanction NOT FOUND")
	void testGetLoanEligibilityInvalid() throws Exception{
		doReturn(sanctionDTO).when(customerService).customerLoanEligibility(-2,3);
		mockMvc.perform(get("/customer/sanction/customerId/loanId",-2,3))
				.andExpect(status().isBadRequest());
	}



	static String asJsonString(Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

}
