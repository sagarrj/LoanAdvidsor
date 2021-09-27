package com.finance.LoanAdvisor.customer;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.finance.LoanAdvisor.config.DataNotFoundException;
import com.finance.LoanAdvisor.entities.Customer;
import com.finance.LoanAdvisor.entities.repository.CustomerRepository;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private final CustomerRepository customerRepository;
	
	Logger logger = LoggerFactory.getLogger(CustomerService.class);

	
	public Optional<Customer> addCustomer(Customer customer) {
		Customer customerInfo = customerRepository.save(customer);
		logger.info("Customer added");
		return Optional.of(customerInfo);
	}

	public Optional<Customer> getCustomer(Integer customerId) throws DataNotFoundException {
		Optional<Customer> customer = customerRepository.findById(customerId);
		if(customer.isEmpty()) {
			logger.warn("Customer not found");
			throw new DataNotFoundException("Customer not found");
		}
		logger.info("Customer returned from service");
		return customer;
	}

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
