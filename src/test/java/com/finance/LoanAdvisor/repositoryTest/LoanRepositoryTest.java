package com.finance.LoanAdvisor.repositoryTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.finance.LoanAdvisor.entities.Loan;
import com.finance.LoanAdvisor.entities.LoanType;
import com.finance.LoanAdvisor.entities.repository.LoanRepository;
import com.finance.LoanAdvisor.loan.VO.LoanVO;



@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
public class LoanRepositoryTest {

	
	@Autowired
	LoanRepository loanRepository;
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
		loanType.setLoanDescription("HomeLoanDes");
		loan.setLoanType(loanType);
	}

	@BeforeEach
	void initEmployeeObject1() {
		loanVO = new LoanVO();
		loanVO.setLoanId(1);
		loanVO.setLoanDesc("HOMELOAN");
		loanVO.setLoanType("HomeLoanDes");
		loanVO.setROI(7.0);

	}
	@Test
	public void testAllLoan() {
		 List<LoanVO> listLoanVO= new ArrayList<>();
		 listLoanVO.add(new LoanVO(1, "HOMELOAN",7.0,null ));
		List<LoanVO> listLoan = Arrays.asList(loanVO);
		List<Loan> allCustomer =loanRepository.findAllByStatus('A');
		assertThat(allCustomer.size()).isGreaterThanOrEqualTo(1);
		
		}
	
	@Test
	public void testFindByIdUser() {
		 List<LoanVO> listLoanVO= new ArrayList<>();
		 listLoanVO.add(new LoanVO(1, "HOMELOAN",7.0,null ));
			List<LoanVO> listLoan = Arrays.asList(loanVO);
			loanRepository.findById(1);
		Assertions.assertEquals(1, loan.getLoanId());

	}
	
	}

