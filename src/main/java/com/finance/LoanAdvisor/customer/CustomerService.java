package com.finance.LoanAdvisor.customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.Optional;

import com.finance.LoanAdvisor.Sanction.VO.SanctionVO;
import com.finance.LoanAdvisor.customer.VO.CustomerEligiblityVO;
import com.finance.LoanAdvisor.entities.Sanction;
import com.finance.LoanAdvisor.entities.repository.SanctionRepository;
import lombok.RequiredArgsConstructor;

import org.hibernate.internal.build.AllowSysOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.finance.LoanAdvisor.config.CustomerNotEligibleException;
import com.finance.LoanAdvisor.config.DataNotFoundException;
import com.finance.LoanAdvisor.customer.VO.CustomerVO;
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
	 * @param customer: {@link Customer}
	 * @return {@link Optional} of {@link Customer}
	 * @throws DataNotFoundException
	 */
	public CustomerVO addCustomer(Customer customer) throws DataNotFoundException{		
		if(customerRepository.findByEmail(customer.getEmail()).isPresent()) {
			logger.warn("Customer is already created");
			throw new DataNotFoundException("Customer is already created");
		}
		customer.setStatus(STATUS);
		customer.setCreateDttm(new Date());
		customer.setCreatedBy(DEFAULT_ID);
	    customerRepository.save(customer);
		CustomerVO customerVO = convertToCustomerVO(customer);
	    logger.info("Customer added");
		return customerVO;
	}


	/**
	 * This method accepts customer Id and returns customer details based on Id.
	 * @param customerId
	 * @return {@link Optional} of {@link Customer}
	 * @throws DataNotFoundException
	 */
	public CustomerVO getCustomer(Integer customerId) throws DataNotFoundException {
		Customer customer = customerRepository.findById(customerId).orElse(null);
		if(customer==null) {
			logger.warn("Customer not found");
			throw new DataNotFoundException("Customer not found");
		}
		CustomerVO customerVO = convertToCustomerVO(customer);
		logger.info("Customer returned from service");
		return customerVO;
	}

	/**
	 * This method returns list of all available customers
	 * @return {@link List} of {@link Customer}
	 * @throws DataNotFoundException
	 */
	public List<CustomerVO> getAllCustomers() throws DataNotFoundException {
		List<Customer> customers = customerRepository.findAllByStatus('A');
		if(customers.isEmpty()) {
			logger.warn("List is empty");
			throw new DataNotFoundException("List is empty");
		}
		List<CustomerVO> customerVOList = convertToCustomerVOList(customers);
		logger.info("List of customers from service");
		return customerVOList;
	}
	

	private List<CustomerVO> convertToCustomerVOList(List<Customer> customers) {
        List<CustomerVO> customerVOList = new ArrayList<>();
        for(Customer customer:customers) {
      	   customerVOList.add(convertToCustomerVO(customer));
        	
        }
		return customerVOList;
	}


	private CustomerVO convertToCustomerVO(Customer customer) {
		CustomerVO customerVO = new CustomerVO();
		customerVO.setCustomerId(customer.getCustomerId());
		customerVO.setFirstName(customer.getFirstName());
		customerVO.setLastName(customer.getLastName());
		customerVO.setEmail(customer.getEmail());
		customerVO.setAge(customer.getAge());
		customerVO.setCreditScore(customer.getCreditScore());
		customerVO.setIncome(customer.getIncome());
		return customerVO;
	}

	/**
	 * This method checks the eliglibity of customers for loan
	 * @throws CustomerNotEligibleException
	 */
	
	public CustomerEligiblityVO customerLoanEliglibity(int customerId, int loanId) {
	Customer customer=customerRepository.findById(customerId).orElse(null);
	Loan loan = loanRepository.findById(loanId).orElse(null);
	int age=0;
	int income=0;
	int creditScore=0;
	Double roi= 0.0;
		String loanDesc=null;
		int loanAmount=0;
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
			 loanDesc = loan.getLoanType().getLoanDesc();

		}
		else {
			throw new DataNotFoundException("Rate of Interest not found");
			}

		if((creditScore>700) && (income>40000) && (age>18 && age<=60)){
				flag = true;
			switch(loanDesc){
			case "GOLD":
				loanAmount=(income/12)*5;
			break;
			case "CAR":
				loanAmount=income/12*20;
			break;
			case "PERSONAL":
				loanAmount=income*3;
			break;
			case "HOME":
				loanAmount=income/12*80;
			break;

			case "EDUCATIONAL":
				loanAmount=income/12*30;
			break;
			}
			}

			else{

				throw new CustomerNotEligibleException("Customer is not eligible for loan");
			}

		if(flag!=true){

		throw new CustomerNotEligibleException("Customer is not eligible for loan");

		}

		Sanction sanction = new Sanction();
		sanction.setLoanAmount(loanAmount);
		return "for loan type "+loanDesc+" you are eligible upto "+loanAmount+ " with ROI "+roi;
	}

	/**
	 * This method accepts and saves sanction details and return an object of
	 * {@link Sanction} containing all arguments which has been saved.
	 * @throws DataNotFoundException
	 */
//	public SanctionVO addDataForSanction(Sanction sanction) throws DataNotFoundException{
//		if(sanctionRepository.findById(sanction.getSanctionId()).isPresent()){
//			logger.warn("Sanction details is already present");
//			throw new DataNotFoundException("Sanction is already created");
//		}
//		 Sanction sanctionInfo = sanctionRepository.save(sanction);
//		SanctionVO sanctionVO= convertToSanctionVO(sanctionInfo.getSanctionId());
//		return sanctionVO;
	}

	private CustomerEligiblityVO convertToEligiblityVO(Integer loanAmount, Double roi) {
		CustomerEligiblityVO customerEligiblityVO= new CustomerEligiblityVO();
		customerEligiblityVO.setLoanAmount(loanAmount);
		customerEligiblityVO.setRoi(roi);
		return customerEligiblityVO;
	}


}
