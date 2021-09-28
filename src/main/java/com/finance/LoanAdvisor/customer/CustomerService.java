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
import com.finance.LoanAdvisor.entities.Loan;
import com.finance.LoanAdvisor.entities.LoanType;
import com.finance.LoanAdvisor.entities.repository.CustomerRepository;
import com.finance.LoanAdvisor.entities.repository.LoanRepository;
import com.finance.LoanAdvisor.entities.repository.LoanTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;



/**
 * @author priypawa
 *
 */
@Service
@RequiredArgsConstructor
public class CustomerService {

	private static final char STATUS = 'A';

	private final CustomerRepository customerRepository;
	private final LoanRepository loanRepository;
	private final LoanTypeRepository loanTypeRepository;
	
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


	
	public boolean customerLoanEliglibity(int customerId, int loanId, int loanTypeId) {
		//Customer customer=customerRepository.findById(customerId).orElse(null);
		Optional<Customer> customer = customerRepository.findById(customerId);
		Loan loan = loanRepository.findById(loanId).orElse(null);
		LoanType  loanType= loanTypeRepository.findById(loanTypeId).orElse(null);
		//System.out.println("Customer "+customer.get());
		int age=0;
		int income=0;
		int creditScore=0;
		Double roi=0.0;
		String loanDesc=null;
		int loanAmount=0;
		if(customer.isPresent()) {
			Customer customerInfo = customer.get();

			age=customerInfo.getAge();
			 income = customerInfo.getIncome();
			 creditScore = customerInfo.getCreditScore();
			//System.out.println(age+""+income+""+creditScore);
		}
		else {
			throw new DataNotFoundException("Customer not found");
			}
		if(loan!=null) {
			 roi = loan.getROI();
		}
		else {
			throw new DataNotFoundException("Rate of Interest not found");
			}
		
		if (loanType!=null) {
			 loanDesc= loanType.getLoanDesc();
		}
		else {
			throw new DataNotFoundException("Loan description not found");
			}
		if(creditScore>700){
			if(income>240000){
			if(age>18 && age<=60){
			switch(loanDesc){
			case "2 wheeler":
			 loanAmount=income/12*5;
			break;
			case "car loan":
			loanAmount=income/12*20;
			break;
			/*case "personal loan":
			loanAmount=income*loanDuration(in years);
			break;*/
			case "home loan":
			loanAmount=income/12*80;
			break;
			}
			//sop("for loan type "+loanDesc+" you are eligible upto "+loanAmount+ "with ROI "+roi);
			}
			/*else{
			"Not Eligible for loan"}
			}
			else{
			"Not Eligible for loan"
			}
			}
			else{

			"Not Eligible for loan"
			}*/
	}
			
	
		}
		return true;
	}
	
	
}
