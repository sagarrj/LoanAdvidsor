package com.finance.LoanAdvisor.customer;

import java.util.List;


import javax.validation.Valid;


import com.finance.LoanAdvisor.Sanction.dto.SanctionDTO;
import com.finance.LoanAdvisor.entities.Sanction;
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

import com.finance.LoanAdvisor.config.DataNotFoundException;
import com.finance.LoanAdvisor.customer.dto.CustomerDTO;
import com.finance.LoanAdvisor.entities.Customer;
import javax.validation.constraints.NotNull;

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
	 * 
	 * @return {@link List} of {@link CustomerDTO}
	 *
	 * @return {@link List} of {@link CustomerVO}
	 *
	 */
	@GetMapping("/list")
	public ResponseEntity<List<CustomerDTO>> getAllCustomers() throws DataNotFoundException{
		logger.info("List of customers from controller");
		List<CustomerDTO> customerDTOList = customerService.getAllCustomers();
		if(customerDTOList.isEmpty())
			throw new DataNotFoundException("List is empty");
		return new ResponseEntity<List<CustomerDTO>>(customerDTOList, HttpStatus.OK);

	}

	/**
	 * This method accepts customer Id and returns customer details based on Id.
	 *
	 * @param customerId : {@link Integer}
	 * @return {@link ResponseEntity} of {@link CustomerDTO}
	 */
	@GetMapping("/view/{id}")
	public ResponseEntity<CustomerDTO> getCustomer(
			@PathVariable("id") @NotNull(message = "Customer Id should not be empty") Integer customerId) throws DataNotFoundException {
		logger.info("Customer returned from controller");
		CustomerDTO customerInfo = customerService.getCustomer(customerId);
		if(customerInfo==null) {
			throw new DataNotFoundException("Customer not found");
		}
		return new ResponseEntity<CustomerDTO>(customerInfo, HttpStatus.OK);
	}

	/**
	 * This method accepts and saves customer details and return an object of
	 * {@link Customer} containing all arguments which has been saved.
	 * 
	 * @param customer : {@link Customer} Object.
	 * @return {@link ResponseEntity} of {@link CustomerDTO}
	 */
	@PostMapping("/add")
	public ResponseEntity<CustomerDTO> addCustomer(@Valid @RequestBody Customer customer) throws DataNotFoundException{
		logger.info("Customer returned from controller");
		CustomerDTO customerInfo = customerService.addCustomer(customer);
		if(customerInfo==null) {
			throw new DataNotFoundException("Customer is already created");
		}
		return new ResponseEntity<CustomerDTO>(customerInfo, HttpStatus.CREATED);
	}

	/**
	 * This gets data from table and return an object of
	 * {@link SanctionDTO} containing all arguments which has been saved
	 * {@link Sanction} Object.
	 * @return {@link ResponseEntity} of {@link SanctionDTO}
	 */
	@GetMapping("/sanction/{customerId}/{loanId}")
	public ResponseEntity<SanctionDTO> loanEligibility(@PathVariable("customerId") Integer customerId, @PathVariable("loanId") Integer loanId) {
		SanctionDTO sanctionInfo = customerService.customerLoanEligibility(customerId, loanId);
		return new ResponseEntity<SanctionDTO>(sanctionInfo,HttpStatus.OK);
	}

}
