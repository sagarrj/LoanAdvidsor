package com.finance.loanadvisor.loan;

import com.finance.loanadvisor.config.ApplicationConstants;
import com.finance.loanadvisor.entities.*;
import com.finance.loanadvisor.entities.repository.BorrowerRepository;
import com.finance.loanadvisor.entities.repository.CustomerRepository;
import com.finance.loanadvisor.entities.repository.LoanRepository;
import com.finance.loanadvisor.entities.repository.SanctionRepository;
import com.finance.loanadvisor.exception.ApplicationException;
import com.finance.loanadvisor.exception.DataNotFoundException;
import com.finance.loanadvisor.loan.dto.LoanDTO;
import com.finance.loanadvisor.loan.dto.RegisterRequest;
import com.finance.loanadvisor.loan.dto.RegisterResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.finance.loanadvisor.config.ApplicationConstants.CANNOT_PROVIDE_LOAN_AFTER;
import static com.finance.loanadvisor.config.ApplicationConstants.MAX_AGE;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

/**
 * @author pkhedkar This class includes all test cases of Customer controller
 *
 */
@SpringBootTest
public class LoanServiceTest {
	@MockBean
	LoanRepository loanRepository;

	@MockBean
	CustomerRepository customerRepository;

	@MockBean
	SanctionRepository sanctionRepository;

	@MockBean
	BorrowerRepository borrowerRepository;

	@Autowired
	LoanService loanService;

	private Customer customer;
	private Sanction sanction;
	private RegisterRequest registerRequest;
	private RegisterResponse registerResponse;
	private Borrower borrower;
	private Loan loan;
	private LoanDTO loanDTO;

	@BeforeEach
	void initEmployeeObject() {
		loan = new Loan();
		loan.setLoanId(1);
		loan.setLoanDesc("Home loan");
		loan.setROI(7.0);
		loan.setStatus('A');
		loan.setCreateDttm(new Date(2021 - 9 - 27));
		loan.setCreatedBy(null);
		loan.setUpdateDttm(null);
		loan.setUpdatedBy(null);
		LoanType loanType = new LoanType();
		loanType.setLoanDescription("HomeLoanDes");
		loan.setLoanType(loanType);

	}

	@BeforeEach
	void initEmployeeObject1() {
		loanDTO = new LoanDTO();
		loanDTO.setLoanId(1);
		loanDTO.setLoanDesc("Home loan");
		loanDTO.setLoanType("HomeLoanDes");
		loanDTO.setROI(7.0);

	}

	/**
	 * This method check test case for list of loan.
	 * 
	 * @throws DataNotFoundException
	 */
	@Test
	@DisplayName(" Get all Loan")
	public void getAllLoan() {
		List<LoanDTO> listLoanDTO = new ArrayList<>();
		listLoanDTO.add(new LoanDTO(1, "Home loan", 7.0, "HomeLoanDes"));
		when(loanRepository.findAllByStatus('A')).thenReturn(Stream.of(loan).collect(Collectors.toList()));
		List<LoanDTO> loanFromService = loanService.getAllLoan();
		Assertions.assertEquals(1, loanFromService.size());
		Assertions.assertEquals(listLoanDTO, loanFromService);

	}

	@Test
	@DisplayName(" Get all Loan not Found")
	public void getAllLoanNotFound() {
		List<LoanDTO> listLoanDTO = new ArrayList<>();
		listLoanDTO.add(new LoanDTO(1, "Home loan", 7.0, "HomeLoanDes"));
		when(loanRepository.findAllByStatus('A')).thenReturn(new ArrayList<>());

		Throwable exception = assertThrows(ApplicationException.class, () -> loanService.getAllLoan());
		Assertions.assertEquals(ApplicationConstants.LIST_IS_EMPTY, exception.getMessage());

	}

	/**
	 * This method check test case for loan on basis of id.
	 * 
	 * @throws DataNotFoundException
	 */
	@Test
	@DisplayName(" Get Loan By Id")
	public void testGetLoanById() {
		doReturn(Optional.of(loan)).when(loanRepository).findById(1);
		Optional<LoanDTO> loan1 = Optional.of(loanService.getLoan(1));
		Assertions.assertTrue(loan1.isPresent());
		Assertions.assertEquals(loanDTO, loan1.get());

	}

	@Test
	@DisplayName(" Get loan Not Found By Id")
	public void getLoanByIdNotFound() {
		when(loanRepository.findById(1)).thenReturn(Optional.ofNullable(null));
		Throwable exception = assertThrows(ApplicationException.class, () -> loanService.getLoan(1));
		Assertions.assertEquals(ApplicationConstants.LOAN_NOT_FOUND, exception.getMessage());
	}

	@BeforeEach
	void initCustomer() {
		customer = new Customer();
		sanction = new Sanction();
		borrower = new Borrower();
	}

	@Test
	@DisplayName("Successful registration")
	public void registerCustomerForLoanSuccess() {
		customer.setAge(50);
		sanction.setROI(6.7);
		sanction.setLoanAmount(2000000.0);

		int customerId = 1;
		int sanctionId = 1;

		registerRequest = new RegisterRequest(customerId, sanctionId, 10);
		registerResponse = new RegisterResponse(10, 22914.0);

		when(customerRepository.findById(customerId)).thenReturn(Optional.ofNullable(customer));
		when(sanctionRepository.findById(sanctionId)).thenReturn(Optional.ofNullable(sanction));
		when(borrowerRepository.save(any(Borrower.class))).thenReturn(borrower);

		RegisterResponse serviceResponse = loanService.registerCustomerForLoan(registerRequest);

		Assertions.assertEquals(registerResponse, serviceResponse);

	}

	@Test
	@DisplayName("Record Not Found for loan registration")
	public void registerCustomerForLoanDataNotFound() {
		int customerId = 1000;
		int sanctionId = 1000;

		registerRequest = new RegisterRequest(customerId, sanctionId, 10);
		when(customerRepository.findById(customerId)).thenReturn(Optional.ofNullable(null));
		when(sanctionRepository.findById(sanctionId)).thenReturn(Optional.ofNullable(null));

		Throwable exception = assertThrows(ApplicationException.class,
				() -> loanService.registerCustomerForLoan(registerRequest));

		Assertions.assertEquals(ApplicationConstants.SANCTION_NOT_FOUND, exception.getMessage());

	}

	@Test
	@DisplayName("Max Age For Registration of Loan")
	public void maxAgeForRegistrationOfLoan() {
		int customerId = 1;
		int sanctionId = 1;
		customer.setAge(65);

		registerRequest = new RegisterRequest(customerId, sanctionId, 10);

		when(customerRepository.findById(customerId)).thenReturn(Optional.ofNullable(customer));
		when(sanctionRepository.findById(sanctionId)).thenReturn(Optional.ofNullable(sanction));

		Throwable exception = assertThrows(ApplicationException.class,
				() -> loanService.registerCustomerForLoan(registerRequest));

		Assertions.assertEquals(CANNOT_PROVIDE_LOAN_AFTER + MAX_AGE, exception.getMessage());

	}

	@Test
	@DisplayName("Invalid Input for loan registration")
	public void invalidInputForRegistrationOfLoan() {
		int customerId = -1;
		int sanctionId = -1;

		registerRequest = new RegisterRequest(customerId, sanctionId, 10);
		Throwable exception = assertThrows(ApplicationException.class,
				() -> loanService.registerCustomerForLoan(registerRequest));

		Assertions.assertEquals(ApplicationConstants.INVALID_INPUT, exception.getMessage());

	}

	@Test
	@DisplayName("Already registered for loan")
	public void alreadyRegisteredForLoan() {

		List<Borrower> borrowerList = new ArrayList<>();
		borrowerList.add(new Borrower());
		int customerId = 1;
		int sanctionId = 1;

		registerRequest = new RegisterRequest(customerId, sanctionId, 10);
		when(borrowerRepository.findByCustomer_customerIdAndSanction_sanctionId(1, 1)).thenReturn(borrowerList);

		Throwable exception = assertThrows(ApplicationException.class,
				() -> loanService.registerCustomerForLoan(registerRequest));

		Assertions.assertEquals(ApplicationConstants.CUSTOMER_ALREADY_REGISTERED_FOR_THIS_LOAN, exception.getMessage());

	}

}
