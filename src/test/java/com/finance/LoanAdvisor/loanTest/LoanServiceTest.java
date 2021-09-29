package com.finance.LoanAdvisor.loanTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.finance.LoanAdvisor.config.LoanConstants;
import com.finance.LoanAdvisor.entities.*;
import com.finance.LoanAdvisor.entities.repository.BorrowerRepository;
import com.finance.LoanAdvisor.entities.repository.CustomerRepository;
import com.finance.LoanAdvisor.entities.repository.SanctionRepository;
import com.finance.LoanAdvisor.loan.VO.RegisterRequest;
import com.finance.LoanAdvisor.loan.VO.RegisterResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.finance.LoanAdvisor.config.DataNotFoundException;
import com.finance.LoanAdvisor.entities.repository.LoanRepository;
import com.finance.LoanAdvisor.loan.LoanService;
import com.finance.LoanAdvisor.loan.VO.LoanVO;

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
	private LoanVO loanVO;

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
		LoanType loanType=new LoanType();
		loanType.setLoanDesc("hOMELOANdES");
		loan.setLoanType(loanType);

	}

	@BeforeEach
	void initEmployeeObject1() {
		loanVO = new LoanVO();
		loanVO.setLoanId(1);
		loanVO.setLoanDesc("HOMELOAN");
		loanVO.setLoanType("hOMELOANdES");
		loanVO.setROI(7.0);

	}

	@Test
	@DisplayName("Test Get all Loan")
	public void getAllLoan() {
         List<LoanVO> listLoan= new ArrayList<>();
		
		listLoan.add(new LoanVO(1, "HOMELOAN",7.0,null ));
		
		when(loanRepository.findAllByStatus('A')).thenReturn(
				Stream.of(loan)
						.collect(Collectors.toList()));
		
		 List<LoanVO> loanFromService=loanService.getAllLoan();
		
		assertEquals(1, loanFromService.size());
		
		
		//Assertions.assertEquals(listLoan,loanFromService);

	}

	@Test
	@DisplayName("Test Get Loan By Id")
	public void testGetLoanById() throws DataNotFoundException {
		doReturn(Optional.of(loan)).when(loanRepository).findById(1);
		Loan loan1 = new Loan();
	//	Assertions.assertTrue(loan1.isPresent());
	//	Assertions.assertSame(loan1.get(), loan);
	}
	
	@BeforeEach
	void initCustomer(){
		customer = new Customer();
		sanction = new Sanction();
		borrower = new Borrower();
	}

	@Test
	@DisplayName("Successful registration")
	public void registerCustomerForLoanSuccess(){
		customer.setAge(50);
		sanction.setROI(6.7);
		sanction.setLoanAmount(2000000.0);


		int customerId=1;
		int sanctionId=1;

		registerRequest = new RegisterRequest(customerId,sanctionId,10);
		registerResponse = new RegisterResponse(10,22914.0);


		when(customerRepository.findById(customerId)).thenReturn(Optional.ofNullable(customer));
		when(sanctionRepository.findById(sanctionId)).thenReturn(Optional.ofNullable(sanction));
		when(borrowerRepository.save(any(Borrower.class))).thenReturn(borrower);

		RegisterResponse serviceResponse = loanService.registerCustomerForLoan(registerRequest);

		Assertions.assertEquals(registerResponse,serviceResponse);

	}

	@Test
	@DisplayName("Data Not Found")
	public void registerCustomerForLoanDataNotFound(){
		int customerId=1000;
		int sanctionId=1000;

		registerRequest = new RegisterRequest(customerId,sanctionId,10);
		when(customerRepository.findById(customerId)).thenReturn(Optional.ofNullable(null));
		when(sanctionRepository.findById(sanctionId)).thenReturn(Optional.ofNullable(null));

		Throwable exception = assertThrows(DataNotFoundException.class,()->{
					loanService.registerCustomerForLoan(registerRequest);
		});

		Assertions.assertEquals(LoanConstants.CUSTOMER_SANCTION_NOT_FOUND,exception.getMessage());

	}
}
