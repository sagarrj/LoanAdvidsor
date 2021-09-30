package com.finance.LoanAdvisor.customer;

import java.util.ArrayList;


import java.util.Date;
import java.util.List;

import com.finance.LoanAdvisor.Sanction.dto.SanctionDTO;
import com.finance.LoanAdvisor.entities.Sanction;
import com.finance.LoanAdvisor.entities.repository.SanctionRepository;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.finance.LoanAdvisor.config.CustomerNotEligibleException;
import com.finance.LoanAdvisor.config.DataNotFoundException;
import com.finance.LoanAdvisor.customer.dto.CustomerDTO;
import com.finance.LoanAdvisor.entities.Customer;
import com.finance.LoanAdvisor.entities.Loan;
import com.finance.LoanAdvisor.entities.repository.CustomerRepository;
import com.finance.LoanAdvisor.entities.repository.LoanRepository;
import com.finance.LoanAdvisor.entities.repository.LoanTypeRepository;


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
	private final LoanRepository loanRepository;
	private final LoanTypeRepository loanTypeRepository;
	private final SanctionRepository sanctionRepository;

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
	 *
	 * @return {@link List} of {@link CustomerVO}
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
	 * This method converts List {@link Customer} object into {@link CustomerVO} object
	 *
	 * @param : {@link Customer}
	 * @return customerVOList: {@link List} of {@link CustomerVO}
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

	/**
	 * This method checks the eligibility of customers for loan and returns the attribute such as
	 * LoanAmount, ROI, LoanDescription
	 * @throws CustomerNotEligibleException
	 */

	public SanctionDTO customerLoanEligibility(int customerId, int loanId) {
	Customer customer=customerRepository.findById(customerId).orElse(null);
	Loan loan = loanRepository.findById(loanId).orElse(null);
	int age=0;
	int income=0;
	int creditScore=0;
	Double roi= 0.0;
		String loanDesc=null;
		double loanAmount=0;
		boolean flag= false;

		if(customer!=null) {
			 age=customer.getAge();
			 income = customer.getIncome();
			 creditScore = customer.getCreditScore();
		}
		else {
			throw new DataNotFoundException("Customer not found");
			}
		if(loan!=null) {
			 roi = loan.getROI();
			 loanDesc = loan.getLoanType().getLoanDescription();
		}
		else {
			throw new DataNotFoundException("Rate of Interest not found");
			}
		if((creditScore>700) && (income>20000) && (age>18 && age<=60)){
				flag = true;
			switch(loanDesc){
			case "GOLD":
				loanAmount=(income/12)*5;
			break;
			case "CAR":
				loanAmount=(income/12)*20;
			break;
			case "PERSONAL":
				loanAmount=income*12*3;
			break;
			case "HOME":
				loanAmount=income*80;
			break;

			case "EDUCATIONAL":
				loanAmount=income*30;
			break;
			}
			}
			else{
				throw new CustomerNotEligibleException("Customer is not eligible for loan");
			}

		if(flag!=true){
		throw new CustomerNotEligibleException("Customer is not eligible for loan");
		}
		Sanction sanction= new Sanction();
		sanction.setCustomer(customer);
		sanction.setLoan(loan);
		sanction.setLoanAmount(loanAmount);
		sanction.setROI(roi);
		sanction.setCreateDttm(new Date());
		sanction.setStatus('A');
		sanctionRepository.save(sanction);
		SanctionDTO sanctionVO = convertTOSanctionVO(sanction);
		return sanctionVO;
	}

	private SanctionDTO convertTOSanctionVO(Sanction sanction){
		SanctionDTO sanctionVO= new SanctionDTO();
		sanctionVO.setLoanAmount(sanction.getLoanAmount());
		sanctionVO.setRoi(sanction.getROI());
		sanctionVO.setLoanType(sanction.getLoan().getLoanType().getLoanDescription());
		return sanctionVO;
	}
}
