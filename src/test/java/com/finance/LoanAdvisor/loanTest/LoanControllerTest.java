package com.finance.LoanAdvisor.loanTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finance.LoanAdvisor.entities.Loan;
import com.finance.LoanAdvisor.loan.LoanController;
import com.finance.LoanAdvisor.loan.LoanService;
import com.finance.LoanAdvisor.loan.VO.LoanVO;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author pkhedkar
 *
 */
@AutoConfigureMockMvc
@SpringBootTest
public class LoanControllerTest {

	@MockBean
	LoanService loanservice;
	private Loan loan;
	@Autowired
	private MockMvc mockMvc;
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
		loan.setLoanType(null);
	}

	@BeforeEach
	void initEmployeeObject1() {
		loanVO = new LoanVO();
		loanVO.setLoanId(1);
		loanVO.setLoanDesc("HOMELOAN");
		loanVO.setLoanType(null);
		loanVO.setROI(7.0);
	}

	/**
	 * This method tests status and {@link List} of {@link Loan} getAllLoan method
	 * {@link LoanController}.
	 *
	 * @throws Exception
	 */
	@DisplayName("GET /getAllLoan")
	@Test
	public void getAllLoan() throws Exception {
		List<LoanVO> listLoan = new ArrayList<>();
		listLoan.add(new LoanVO(1, "HOMELOAN", 7.0, null));
		Mockito.when(loanservice.getAllLoan()).thenReturn(listLoan);
		String url = "/loan/getAllLoan";
		mockMvc.perform(get(url)).andExpect(status().isOk());
	}

	/**
	 * This method check test status {@link Loan} on basis of id.
	 *
	 * @throws Exception
	 */

	@DisplayName("GET/geLoanById")
	@Test
	public void LoanById() throws Exception {

		Mockito.when(loanservice.getLoan(1)).thenReturn(loanVO);
		String url = "/loan/get/loan/1";
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

	@Test
	@DisplayName("GetAllLoanNotFound")
	public void GetAllLoanNotFound() {
		List<LoanVO> listLoan = new ArrayList<>();
		listLoan.add(new LoanVO(1, "HOMELOAN", 7.0, null));
		Mockito.when(loanservice.getAllLoan()).thenReturn(listLoan);
		try {
			mockMvc.perform(
					get("/loan/getAllLoan" + loanVO.getLoanId().toString()).contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isNotFound());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	@DisplayName("GET/geLoanByIdNotFound")
//	@Test
//	public void LoanByIdNotFound() throws Exception {
////
////		Mockito.when( loanservice.getLoan(1)).thenReturn(loanVO);
////		mockMvc.perform(get("/loan/get/loan/" + loanVO.getLoanId().toString())
////			     .contentType(MediaType.APPLICATION_JSON))
////			     .andExpect(status().isNotFound());
//
//		Mockito.doThrow(new DataNotFoundException()).when(loanservice).getLoan(1);
//		mockMvc.perform(get("/loan/get/loan/" + loanVO.getLoanId().toString()).contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().isNotFound());
//
//	}

}
