package com.finance.LoanAdvisor.loanTest;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.finance.LoanAdvisor.config.DataNotFoundException;
import com.finance.LoanAdvisor.entities.Loan;
import com.finance.LoanAdvisor.entities.LoanType;
import com.finance.LoanAdvisor.entities.repository.LoanRepository;
import com.finance.LoanAdvisor.loan.LoanService;
import com.finance.LoanAdvisor.loan.VO.LoanVO;

@SpringBootTest
public class LoanServiceTest {
	@MockBean
	LoanRepository loanRepository;
	@Autowired
	LoanService loanService;
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
		
		Assertions.assertEquals(1, loanFromService.size());
		
		
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
}
