package com.finance.loanadvisor.customer;

import com.finance.loanadvisor.Sanction.dto.SanctionDTO;


import com.finance.loanadvisor.customer.dto.CustomerDTO;
import com.finance.loanadvisor.entities.Customer;
import com.finance.loanadvisor.entities.Sanction;
import com.finance.loanadvisor.exception.ApplicationException;
import com.finance.loanadvisor.exception.DataNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import java.util.List;

/**
 * @author priypawa This class is controller of {@link Customer} entity which
 *         contains REST api methods.
 *
 */
@Validated
@RestController
@RequestMapping("/customer")
public class CustomerController {

	private static final String CUSTOMER_IS_ALREADY_CREATED = "Customer is already created";

	private static final String CUSTOMER_NOT_FOUND = "Customer not found";

	private static final String LIST_IS_EMPTY = "List is empty";

	@Autowired
	CustomerService customerService;

	Logger logger = LoggerFactory.getLogger(CustomerController.class);

	/**
	 * This method returns list of available customers
	 * 
	 * @return {@link List} of {@link CustomerDTO}
	 *
	 * @return {@link List} of {@link CustomerDTO}
	 *
	 */
	@GetMapping("/list")
	public ResponseEntity<List<CustomerDTO>> getAllCustomers() throws ApplicationException {
		List<CustomerDTO> customerDTOList = customerService.getAllCustomers();
		if (customerDTOList.isEmpty()) {
			throw new ApplicationException(LIST_IS_EMPTY);
		}
		logger.info("List of customers from controller");
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
			@PathVariable("id") @Min(value = 1, message = "Customer Id must be greater than or equal to 1") @Max(value = 1000, message = "Customer Id must be lower than or equal to 1000") Integer customerId)
			throws ApplicationException {
		CustomerDTO customerInfo = customerService.getCustomer(customerId);
		if (customerInfo == null) {
			throw new ApplicationException(CUSTOMER_NOT_FOUND);
		}
		logger.info("Customer returned from controller");
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
	public ResponseEntity<CustomerDTO> addCustomer(@Valid @RequestBody Customer customer) throws ApplicationException {
		CustomerDTO customerInfo = customerService.addCustomer(customer);
		if (customerInfo == null) {
			throw new ApplicationException(CUSTOMER_IS_ALREADY_CREATED);
		}
		logger.info("Customer returned from controller");
		return new ResponseEntity<CustomerDTO>(customerInfo, HttpStatus.CREATED);
	}

	/**
	 * This gets data from table and return an object of {@link SanctionDTO}
	 * containing all arguments which has been saved {@link Sanction} Object.
	 *
	 * @return {@link ResponseEntity} of {@link SanctionDTO}
	 */
	@GetMapping("/sanction/{customerId}/{loanId}")
	public ResponseEntity<SanctionDTO> loanEligibility(@PathVariable("customerId") Integer customerId, @PathVariable("loanId") Integer loanId) throws ApplicationException {
		SanctionDTO sanctionInfo = customerService.customerLoanEligibility(customerId, loanId);
		return new ResponseEntity<SanctionDTO>(sanctionInfo, HttpStatus.OK);
	}

}
