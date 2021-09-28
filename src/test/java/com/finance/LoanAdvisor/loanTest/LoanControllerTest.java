package com.finance.LoanAdvisor.loanTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.finance.LoanAdvisor.entities.Loan;
import com.finance.LoanAdvisor.loan.LoanService;

@AutoConfigureMockMvc
@SpringBootTest
public class LoanControllerTest {
	
	@MockBean
	LoanService loanservice;
	private Loan loan;
	@Autowired
	private MockMvc mockMvc;
	
	


}
