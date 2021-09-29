package com.finance.LoanAdvisor.loanTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finance.LoanAdvisor.entities.Loan;
import com.finance.LoanAdvisor.entities.LoanType;
import com.finance.LoanAdvisor.loan.LoanService;
import com.finance.LoanAdvisor.loan.VO.LoanVO;


@AutoConfigureMockMvc
@SpringBootTest
public class LoanControllerTest {
	
	@MockBean
	LoanService loanservice;
	@Autowired
	private MockMvc mockMvc;
	private LoanVO loanVO;

//	@BeforeEach
//	void initEmployeeObject() {
//		loan = new LoanVO();
//		loan.setLoanId(1);
//		loan.setLoanDesc("Home loan");
//		loan.setROI(7.0);
//		loan.setStatus('A');
//		loan.setCreateDttm(new Date(2021-9-27));
//		loan.setCreatedBy(null);
//		loan.setUpdateDttm(null);
//		loan.setUpdatedBy(null);
//		loan.setLoanType(null);
//		
//	}
	
	@BeforeEach
	void initEmployeeObject1() {
		loanVO = new LoanVO();
		loanVO.setLoanId(1);
		loanVO.setLoanDesc("HOMELOAN");
		loanVO.setLoanType(null);
		loanVO.setROI(7.0);

	}


	@DisplayName("GET /getAllLoan")
	@Test
	public void testListLoan() throws Exception {
		List<LoanVO> listLoan= new ArrayList<>();
		
		//loanType.add(new LoanType(1,"HomeLoan" ,'A',null,null,null,null));
		listLoan.add(new LoanVO(1, "HOMELOAN",7.0,null ));
		Mockito.when(loanservice.getAllLoan()).thenReturn(listLoan);
		String url = "/loan/getAllLoan";
		mockMvc.perform(get(url)).andExpect(status().isOk());
	}
	
	
	
	@DisplayName("GET/geLoanById")
	@Test
	public void testLoanById() throws Exception {
	
		Mockito.when( loanservice.getLoan(1)).thenReturn(loanVO);
		String url = "/loan/getLoan/1";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String inputJson = this.mapToJson(loanVO);
		String outputJson = result.getResponse().getContentAsString();
		assertThat(outputJson).isEqualTo(inputJson);
         mockMvc.perform(get(url)).andExpect(status().isOk());

	}
	
	
	
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
// TODO Auto-generated method stub
		return objectMapper.writeValueAsString(object);
	}

}
