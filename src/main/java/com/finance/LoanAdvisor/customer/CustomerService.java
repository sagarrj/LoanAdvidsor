package com.finance.LoanAdvisor.customer;

import java.util.Date;
import java.util.List;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.finance.LoanAdvisor.config.DataNotFoundException;
import com.finance.LoanAdvisor.entities.Customer;
import com.finance.LoanAdvisor.entities.repository.CustomerRepository;

/**
 * @author priypawa
 *
 */
@Service
@RequiredArgsConstructor
public class CustomerService {

	private static final char STATUS = 'A';

	private final CustomerRepository customerRepository;
	
	Logger logger = LoggerFactory.getLogger(CustomerService.class);

	
	/**
	 * This method accepts and saves customer details and return an object of
	 * {@link Customer} containing all arguments which has been saved.
	 * @param customer: {@link Customer}
	 * @return {@link Optional} of {@link Customer}
	 * @throws DataNotFoundException
	 */
	public Optional<Customer> addCustomer(Customer customer) throws DataNotFoundException{		
		if(customerRepository.findByEmail(customer.getEmail()).isPresent()) {
			logger.warn("Customer is already created");
			throw new DataNotFoundException("Customer is already created");
		}
		customer.setStatus(STATUS);
		customer.setCreateDttm(new Date());
		customer.setCreatedBy(0);
		Customer customerInfo = customerRepository.save(customer);
		logger.info("Customer added");
		return Optional.of(customerInfo);
	}

	/**
	 * This method accepts customer Id and returns customer details based on Id.
	 * @param customerId
	 * @return {@link Optional} of {@link Customer}
	 * @throws DataNotFoundException
	 */
	public Optional<Customer> getCustomer(Integer customerId) throws DataNotFoundException {
		Optional<Customer> customer = customerRepository.findById(customerId);
		if(customer.isPresent()) {
			logger.warn("Customer not found");
			throw new DataNotFoundException("Customer not found");
		}
		logger.info("Customer returned from service");
		return customer;
	}

	/**
	 * This method returns list of all available customers
	 * @return {@link List} of {@link Customer}
	 * @throws DataNotFoundException
	 */
	public List<Customer> getAllCustomers() throws DataNotFoundException {
		List<Customer> customers = customerRepository.findAll();
		if(customers.isEmpty()) {
			logger.warn("List is empty");
			throw new DataNotFoundException("List is empty");
		}
		logger.info("List of customers from service");
		return customers;
	}
}
