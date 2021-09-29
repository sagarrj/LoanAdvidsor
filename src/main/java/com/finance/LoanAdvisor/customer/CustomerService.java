package com.finance.LoanAdvisor.customer;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.finance.LoanAdvisor.config.DataNotFoundException;
import com.finance.LoanAdvisor.customer.VO.CustomerVO;
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
	 * @return customerVO :{@link CustomerVO}
	 * @throws DataNotFoundException
	 */
	public CustomerVO addCustomer(Customer customer) throws DataNotFoundException {
		if (customerRepository.findByEmail(customer.getEmail()) != null) {
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
	 * 
	 * @param customerId
	 * @return customerVO :{@link CustomerVO}
	 * @throws DataNotFoundException
	 */
	public CustomerVO getCustomer(Integer customerId) throws DataNotFoundException {
		Customer customer = customerRepository.findById(customerId).orElse(null);
		if (customer == null) {
			logger.warn("Customer not found");
			throw new DataNotFoundException("Customer not found");
		}
		CustomerVO customerVO = convertToCustomerVO(customer);
		logger.info("Customer returned from service");
		return customerVO;
	}

	/**
	 * This method returns list of all available customers
	 * 
	 * @return {@link List} of {@link CustomerVO}
	 * @throws DataNotFoundException
	 */
	public List<CustomerVO> getAllCustomers() throws DataNotFoundException {
		List<Customer> customers = customerRepository.findAllByStatus(STATUS);
		if (customers.isEmpty()) {
			logger.warn("List is empty");
			throw new DataNotFoundException("List is empty");
		}
		List<CustomerVO> customerVOList = convertToCustomerVOList(customers);
		logger.info("List of customers from service");
		return customerVOList;
	}

	/**
	 * This method converts List {@link Customer} object into {@link CustomerVO} object
	 * 
	 * @param customers : {@link Customer}
	 * @return customerVOList: {@link List} of {@link CustomerVO}
	 */
	public List<CustomerVO> convertToCustomerVOList(List<Customer> customerList) {
		List<CustomerVO> customerVOList = new ArrayList<>();
		for (Customer customer : customerList) {
			customerVOList.add(convertToCustomerVO(customer));
		}
		return customerVOList;
	}

	public CustomerVO convertToCustomerVO(Customer customer) {
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
}
