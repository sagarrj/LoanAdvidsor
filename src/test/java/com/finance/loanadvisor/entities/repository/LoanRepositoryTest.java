package com.finance.loanadvisor.entities.repository;

import com.finance.loanadvisor.config.ApplicationConstants;
import com.finance.loanadvisor.entities.Loan;
import com.finance.loanadvisor.entities.LoanType;
import com.finance.loanadvisor.loan.dto.LoanDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author pkhedkar
 *
 */
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
public class LoanRepositoryTest {

	@Autowired
	LoanRepository loanRepository;
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
		loanDTO.setLoanDesc("HOMELOAN");
		loanDTO.setLoanType("HomeLoanDes");
		loanDTO.setROI(7.0);

	}

	/**
	 * This method test case list of all loan.
	 */
	@Test
	public void AllLoan() {
		List<LoanDTO> listLoanDTO = new ArrayList<>();
		listLoanDTO.add(new LoanDTO(1, "HOMELOAN", 7.0, null));
		Arrays.asList(loanDTO);
		List<Loan> allCustomer = loanRepository.findAllByStatus(ApplicationConstants.ACTIVE);
		assertThat(allCustomer.size()).isGreaterThanOrEqualTo(1);

	}

	/**
	 * This method test case on base of id
	 */
	@Test
	public void getLoan() {
		List<LoanDTO> listLoanDTO = new ArrayList<>();
		listLoanDTO.add(new LoanDTO(1, "HOMELOAN", 7.0, null));
		Arrays.asList(loanDTO);
		loanRepository.findById(1);
		Assertions.assertEquals(1, loan.getLoanId());

	}

}
