package com.finance.LoanAdvisor.customer;


import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finance.LoanAdvisor.entities.Customer;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	@GetMapping("/list")
	public List<Customer> getAllCustomers() {
		logger.info("List of customers from controller");
		return customerService.getAllCustomers();
	}
	
	@GetMapping("/view/{id}")
	public ResponseEntity<Customer> getCustomer(@PathVariable("id") Integer customerId) {
		logger.info("Customer returned from controller");
		Optional<Customer> customerInfo =  customerService.getCustomer(customerId);
		return new ResponseEntity<Customer>(customerInfo.get(),HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer){
		logger.info("Customer returned from controller");
		Optional<Customer> customerInfo = customerService.addCustomer(customer);
		return new ResponseEntity<Customer>(customerInfo.get(),HttpStatus.CREATED);

	}

}
