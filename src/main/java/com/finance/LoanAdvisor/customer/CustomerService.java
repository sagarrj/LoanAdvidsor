package com.finance.LoanAdvisor.customer;

import java.util.ArrayList;


import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.finance.LoanAdvisor.config.DataNotFoundException;
import com.finance.LoanAdvisor.customer.dto.CustomerDTO;
import com.finance.LoanAdvisor.entities.Customer;
import com.finance.LoanAdvisor.entities.repository.CustomerRepository;

/**
 * @author priypawa
 *
 */
@Service
@RequiredArgsConstructor
public class CustomerService {

	private static final int DEFAULT_ID = 0;

	private static final char STATUS = 'A';

	private final CustomerRepository customerRepository;

	Logger logger = LoggerFactory.getLogger(CustomerService.class);

	/**
	 * This method accepts and saves customer details and return an object of
	 * {@link Customer} containing all arguments which has been saved.
	 * 
	 * @param customer: {@link Customer}
	 * @return customerVO :{@link CustomerDTO}
	 * @throws DataNotFoundException
	 */
	public CustomerDTO addCustomer(Customer customer) throws DataNotFoundException {
		if (customerRepository.findByEmail(customer.getEmail()) != null) {
			logger.warn("Customer is already created");
			throw new DataNotFoundException("Customer is already created");
		}
		customer.setStatus(STATUS);
		customer.setCreateDttm(new Date());
		customer.setCreatedBy(DEFAULT_ID);
		customerRepository.save(customer);
		CustomerDTO customerDTO = convertToCustomerDTO(customer);
		logger.info("Customer added");
		return customerDTO;
	}

	/**
	 * This method accepts customer Id and returns customer details based on Id.
	 * 
	 * @param customerId
	 * @return customerVO :{@link CustomerDTO}
	 * @throws DataNotFoundException
	 */
	public CustomerDTO getCustomer(Integer customerId) throws DataNotFoundException {
		Customer customer = customerRepository.findById(customerId).orElse(null);
		if (customer == null) {
			logger.warn("Customer not found");
			throw new DataNotFoundException("Customer not found");
		}
		CustomerDTO customerDTO = convertToCustomerDTO(customer);
		logger.info("Customer returned from service");
		return customerDTO;
	}

	/**
	 * This method returns list of all available customers
	 * 
	 * @return {@link List} of {@link CustomerDTO}
	 * @throws DataNotFoundException
	 */
	public List<CustomerDTO> getAllCustomers() throws DataNotFoundException {
		List<Customer> customers = customerRepository.findAllByStatus(STATUS);
		if (customers.isEmpty()) {
			logger.warn("List is empty");
			throw new DataNotFoundException("List is empty");
		}
		List<CustomerDTO> customerDTOList = convertToCustomerDTOList(customers);
		logger.info("List of customers from service");
		return customerDTOList;
	}

	/**
	 * This method converts List {@link Customer} object into {@link CustomerDTO} object
	 * 
	 * @param customers : {@link Customer}
	 * @return customerVOList: {@link List} of {@link CustomerDTO}
	 */
	public List<CustomerDTO> convertToCustomerDTOList(List<Customer> customerList) {
		List<CustomerDTO> customerDTOList = new ArrayList<>();
		for (Customer customer : customerList) {
			customerDTOList.add(convertToCustomerDTO(customer));
		}
		return customerDTOList;
	}

	public CustomerDTO convertToCustomerDTO(Customer customer) {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setCustomerId(customer.getCustomerId());
		customerDTO.setFirstName(customer.getFirstName());
		customerDTO.setLastName(customer.getLastName());
		customerDTO.setEmail(customer.getEmail());
		customerDTO.setAge(customer.getAge());
		customerDTO.setCreditScore(customer.getCreditScore());
		customerDTO.setIncome(customer.getIncome());
		return customerDTO;
	}
	
	
}
