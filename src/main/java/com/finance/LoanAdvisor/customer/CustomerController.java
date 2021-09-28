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

import com.finance.LoanAdvisor.customer.VO.CustomerVO;
import com.finance.LoanAdvisor.entities.Customer;


/**
 * @author priypawa
 *
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	/**
	 * This method returns list of available customers
	 * @return {@link List} of {@link Customer}
	 */
	@GetMapping("/list")
	public List<CustomerVO> getAllCustomers() {
		logger.info("List of customers from controller");
		return customerService.getAllCustomers();
	}

	/**
     * This method accepts customer Id and returns customer details based on Id.
	 * @param customerId : {@link Integer}
	 * @return {@link ResponseEntity} of {@link Customer}
	 */
	@GetMapping("/view/{id}")
	
	public ResponseEntity<CustomerVO> getCustomer(@PathVariable("id") Integer customerId) {
		logger.info("Customer returned from controller");
		CustomerVO customerInfo =  customerService.getCustomer(customerId);
		return new ResponseEntity<CustomerVO>(customerInfo,HttpStatus.OK);
	}
	/**
	 * This method accepts and saves customer details and return an object of
	 * {@link Customer} containing all arguments which has been saved.
	 * 
	 * @param customer : {@link Customer} Object.
	 * @return {@link ResponseEntity} of {@link Customer} 
	 */
	@PostMapping("/add")
	public ResponseEntity<CustomerVO> addCustomer(@RequestBody Customer customer){
		logger.info("Customer returned from controller");
		CustomerVO customerInfo = customerService.addCustomer(customer);
		return new ResponseEntity<CustomerVO>(customerInfo,HttpStatus.CREATED);
	}


}
