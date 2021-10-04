package com.finance.loanadvisor.customer;

import com.finance.loanadvisor.Sanction.dto.SanctionDTO;

import com.finance.loanadvisor.customer.dto.CustomerDTO;
import com.finance.loanadvisor.entities.Customer;
import com.finance.loanadvisor.entities.Loan;
import com.finance.loanadvisor.entities.Sanction;
import com.finance.loanadvisor.entities.repository.CustomerRepository;
import com.finance.loanadvisor.entities.repository.LoanRepository;
import com.finance.loanadvisor.entities.repository.SanctionRepository;
import com.finance.loanadvisor.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author priypawa This service class contains business logic of
 *         {@link Customer} REST api methods.
 *
 */
@Service
@RequiredArgsConstructor
public class CustomerService {

	@Autowired
	ModelMapper modelMapper;

	private static final String LIST_IS_EMPTY = "List is empty";

	private static final String CUSTOMER_NOT_FOUND = "Customer not found";

	private static final String CUSTOMER_IS_ALREADY_CREATED = "Customer is already created";

	private static final int DEFAULT_ID = 0;

	private static final char STATUS = 'A';

	private final CustomerRepository customerRepository;
	private final LoanRepository loanRepository;
	private final SanctionRepository sanctionRepository;
	Sanction sanction = new Sanction();

	Logger logger = LoggerFactory.getLogger(CustomerService.class);

	/**
	 * This method accepts and saves customer details and return an object of
	 * {@link Customer} containing all arguments which has been saved.
	 *
	 * @param customer: {@link Customer}
	 * @return customerVO :{@link CustomerDTO}
	 * @throws ApplicationException
	 */
	public CustomerDTO addCustomer(Customer customer) throws ApplicationException {
		if (customerRepository.findByEmail(customer.getEmail()) != null) {
			logger.warn(CUSTOMER_IS_ALREADY_CREATED);
			throw new ApplicationException(CUSTOMER_IS_ALREADY_CREATED);
		}
		if (validateCustomerData(customer)) {
			customer.setCreditScore((int) (Math.random() * 1000));
			customer.setStatus(STATUS);
			customer.setCreateDttm(new Date());
			customer.setCreatedBy(DEFAULT_ID);
			customerRepository.save(customer);
		}

		CustomerDTO customerDTO = convertToCustomerDTO(customer);
		logger.info("Customer added");
		return customerDTO;
	}

	/**
	 * This method accepts customer Id and returns customer details based on Id.
	 *
	 * @param customerId
	 * @return customerVO :{@link CustomerDTO}
	 * @throws ApplicationException
	 */
	public CustomerDTO getCustomer(Integer customerId) throws ApplicationException {
		Customer customer = customerRepository.findById(customerId).orElse(null);
		if (customer == null) {
			logger.warn(CUSTOMER_NOT_FOUND);
			throw new ApplicationException(CUSTOMER_NOT_FOUND);
		}
		CustomerDTO customerDTO = convertToCustomerDTO(customer);
		logger.info("Customer returned from service");
		return customerDTO;
	}

	/**
	 * This method returns list of all available customers
	 * 
	 * @return {@link List} of {@link CustomerDTO}
	 * @throws ApplicationException
	 */
	public List<CustomerDTO> getAllCustomers() throws ApplicationException {
		List<Customer> customers = customerRepository.findAllByStatus(STATUS);
		if (customers.isEmpty()) {
			logger.warn(LIST_IS_EMPTY);
			throw new ApplicationException(LIST_IS_EMPTY);
		}
		List<CustomerDTO> customerDTOList = convertToCustomerDTOList(customers);
		logger.info("List of customers from service");
		return customerDTOList;
	}

	/**
	 * This method converts List {@link Customer} into {@link List} of
	 * {@link CustomerDTO}
	 * 
	 * @param customerList : {@link Customer}
	 * @return customerVOList: {@link List} of {@link CustomerDTO}
	 */
	public List<CustomerDTO> convertToCustomerDTOList(List<Customer> customerList) {
		List<CustomerDTO> customerDTOList = new ArrayList<>();
		for (Customer customer : customerList) {
			customerDTOList.add(convertToCustomerDTO(customer));
		}
		return customerDTOList;
	}

	/**
	 * This method maps properties of {@link Customer} object to {@link CustomerDTO}
	 * object using {@link ModelMapper}
	 * 
	 * @param customer : {@link Customer}
	 * @return customerDTO : {@link CustomerDTO}
	 */
	public CustomerDTO convertToCustomerDTO(Customer customer) {
		CustomerDTO customerDTO = new CustomerDTO();
		modelMapper.map(customer, customerDTO);
		return customerDTO;

	}

	/**
	 * This method validates customer data such as age, income, loan amount, initial
	 * requirement based on given conditions
	 * 
	 * @param customer : {@link Customer}
	 * @return flag : {@link Boolean}
	 * @throws ApplicationException
	 */
	public boolean validateCustomerData(Customer customer) throws ApplicationException {
		boolean flag = false;
		if ((customer.getAge() >= 18 && customer.getAge() <= 60)) {
			flag = true;
		} else {
			flag = false;
			throw new ApplicationException("Age must be between 18 to 60");
		}
		if (customer.getLoanRequirement() <= 10000000) {
			flag = true;
		} else {
			flag = false;
			throw new ApplicationException("Loan requirement must be lower than or equal to 1000000");
		}
		if (customer.getInitialAmount() < customer.getLoanRequirement()) {
			flag = true;
		} else {
			flag = false;
			throw new ApplicationException("Initial amount should be less than loan requirement");
		}
		if (customer.getIncome() < 10000000) {
			flag = true;
		} else {
			flag = false;
			throw new ApplicationException("Income should be less than 10000000");
		}
		return flag;
	}

	/**
	 * This method checks the eligibility of customers for loan and returns the
	 * attribute such as LoanAmount, ROI, LoanDescription
	 * 
	 * @throws ApplicationException
	 */

	public SanctionDTO customerLoanEligibility(int customerId, int loanId) throws ApplicationException{
		if (customerId < 1 && loanId < 1) {
			throw new ApplicationException("CustomerId and LoanId should not be less then one");
		}
		else {
			Customer customer = customerRepository.findById(customerId).orElse(null);
			Loan loan = loanRepository.findById(loanId).orElse(null);

			int age = 0;
			int income = 0;
			int creditScore = 0;
			Double roi = 0.0;
			String loanDesc = null;
			double maxLoanAmount = 0;
			double loanRequirement = 0;

			if (customer != null) {
				age = customer.getAge();
				income = customer.getIncome();
				creditScore = customer.getCreditScore();
				loanRequirement = customer.getLoanRequirement();
			} else {
				throw new ApplicationException("Customer not found");
			}
			if (loan != null) {
				roi = loan.getROI();
				loanDesc = loan.getLoanType().getLoanDescription();
			} else {
				throw new ApplicationException("Loan Details not found");
			}
			if ((creditScore > 700) && (income > 20000) && (age > 18 && age <= 60)) {
				switch (loanDesc) {
				case "GOLD":
					maxLoanAmount = (income * 12) * 3;
					break;
				case "CAR":
					maxLoanAmount = (income * 12) * 4;
					break;
				case "PERSONAL":
					maxLoanAmount = income * 12 * 3;
					break;
				case "HOME ":
					maxLoanAmount = income * 12 * 20;
					break;

				case "EDUCATIONAL":
					maxLoanAmount = 8000000;
					break;
				}
			} else {
				throw new ApplicationException("Customer is not eligible for loan");
			}
			sanction.setROI(roi);
			if (customer.getGender().equalsIgnoreCase("Female") && customer.getAge() < 40) {
				sanction.setROI(sanction.getROI() - 0.05);
			}
			if (customer.getCreditScore() > 800) {
				sanction.setROI(sanction.getROI() - 0.02);
			} else if (customer.getCreditScore() > 850) {
				sanction.setROI(sanction.getROI() - 0.03);
			}
			if (loanRequirement < maxLoanAmount) {
				sanction.setLoanAmount(loanRequirement);
			} else if (loanRequirement > maxLoanAmount) {
				throw new ApplicationException("Customer is not eligible for loan");
			}
			sanction.setCustomer(customer);
			sanction.setLoan(loan);
			sanction.setCreateDttm(new Date());
			sanction.setStatus('A');
			sanctionRepository.save(sanction);

			SanctionDTO sanctionVO = convertTOSanctionVO(sanction);
			return sanctionVO;

		}
	}

	public SanctionDTO convertTOSanctionVO(Sanction sanction) {
		SanctionDTO sanctionVO = new SanctionDTO();
		sanctionVO.setLoanAmount(sanction.getLoanAmount());
		sanctionVO.setRoi(sanction.getROI());
		sanctionVO.setLoanType(sanction.getLoan().getLoanType().getLoanDescription());
		return sanctionVO;
	}
}
