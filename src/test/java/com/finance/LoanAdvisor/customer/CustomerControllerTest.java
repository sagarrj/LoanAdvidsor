package com.finance.LoanAdvisor.customer;

import static org.hamcrest.Matchers.is;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finance.LoanAdvisor.customer.dto.CustomerDTO;
import com.finance.LoanAdvisor.entities.Customer;

/**
 * @author priypawa This class includes all test cases of Customer controller.
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {


	@MockBean
	CustomerService customerService;

	@Autowired
	private MockMvc mockMvc;
	private Customer customer;
	private CustomerDTO customerVO;

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

	/**
	 * This method tests status, mediaType and {@link List} of {@link Customer} of
	 * getAllCustomer method {@link CustomerController}.
	 * 
	 * @throws Exception
	 */
	@Test
	@DisplayName("Test GET /list --Success")
	void testgetAllcustomersValid() throws Exception {
		List<CustomerDTO> savedCustomerVOList = new ArrayList<>();
		savedCustomerVOList.add(customerVO);
		doReturn(savedCustomerVOList).when(customerService).getAllCustomers();
		mockMvc.perform(get("/customer/list"))
				// Validate status and mediaType
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
		assertNotNull(savedCustomerVOList);
	}
	
	@Test
	@DisplayName("Test GET /list --Not Found")
	void testgetAllcustomersInvalid() throws Exception {
		List<CustomerDTO> savedCustomerDTOList = new ArrayList<>();
		doReturn(savedCustomerDTOList).when(customerService).getAllCustomers();
		mockMvc.perform(get("/customer/list"))
		// Validate status and mediaType
		.andExpect(status().isNotFound());

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
		doReturn(customerVO).when(customerService)
				.addCustomer(ArgumentMatchers.any());
		mockMvc.perform(post("/customer/add").contentType(MediaType.APPLICATION_JSON).content(asJsonString(customerVO)))
				// Validate status and mediaType
				.andExpect(status().is(201)).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("customerId", is(10))).andExpect(jsonPath("firstName", is("Pooja")))
				.andExpect(jsonPath("lastName", is("Patil"))).andExpect(jsonPath("email", is("poojapatil@gmail.com")))
				.andExpect(jsonPath("age", is(31))).andExpect(jsonPath("income", is(70000)))
				.andExpect(jsonPath("creditScore", is(900)));
		Assertions.assertNotNull(customerVO);
	}
	
	@Test
	@DisplayName("Test POST /add --Not Found")
	void testAddCustomerInvalid() throws Exception {
		CustomerDTO savedCustomerVO = new CustomerDTO();	
		mockMvc.perform(
				post("/customer/add").contentType(MediaType.APPLICATION_JSON).content(asJsonString(savedCustomerVO)))
				.andExpect(status().isNotFound());
	}

	static String asJsonString(Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

}
