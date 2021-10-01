package com.finance.loanadvisor.loan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finance.loanadvisor.entities.Loan;
import com.finance.loanadvisor.loan.dto.LoanDTO;
import com.finance.loanadvisor.loan.dto.RegisterRequest;
import com.finance.loanadvisor.loan.dto.RegisterResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author pkhedkar
 *
 */
@AutoConfigureMockMvc
@SpringBootTest
@WithMockUser(username="admin")
public class LoanControllerTest {

	@MockBean
	LoanService loanservice;
	private Loan loan;
	@Autowired
	private MockMvc mockMvc;

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
		loan.setLoanType(null);
	}

	@BeforeEach
	void initEmployeeObject1() {
		loanDTO = new LoanDTO();
		loanDTO.setLoanId(1);
		loanDTO.setLoanDesc("HOMELOAN");
		loanDTO.setLoanType(null);
		loanDTO.setROI(7.0);
	}

	/**
	 * This method tests status and {@link List} of {@link Loan} getAllLoan method
	 * {@link LoanController}.
	 *
	 * @throws Exception
	 */
	
	@Test
	@DisplayName(" Test Get AllLoan")
	public void testGetAllLoan() throws Exception  {
		List<LoanDTO> listLoanDTO = new ArrayList<>();
		listLoanDTO.add(new LoanDTO(1, "HOMELOAN", 7.0, null));
		when(loanservice.getAllLoan()).thenReturn(listLoanDTO);
		String url = "/loan/list";
		mockMvc.perform(get(url)).andExpect(status().isOk());
		
	}

	/**
	 * This method tests status and {@link List} of {@link Loan} testGetAllLoanNotFound method
	 * @throws Exception
	 */
	@Test
	@DisplayName("Test Get AllLoan Not Found")
	public void testGetAllLoanNotFound() throws Exception
	{
		List<LoanDTO> listLoanDTO = new ArrayList<>();
		listLoanDTO.add(new LoanDTO(1, "HOMELOAN", 7.0, null));
		when(loanservice.getAllLoan()).thenReturn(listLoanDTO);
			mockMvc.perform(
					get("/loan/list" +loanDTO.getLoanId().toString()).contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isNotFound());

	}

	/**
	 * This method check test status {@link Loan} on basis of id.
	 *
	 * @throws Exception
	 */

	
	@Test
	@DisplayName("Test Get Loan By Id")
	public void testGetLoan() throws Exception {

		when(loanservice.getLoan(1)).thenReturn(loanDTO);
		String url = "/loan/view/1";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String inputJson = this.mapToJson(loanDTO);
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
	@DisplayName("Test Get Loan Not Found")
	public void testGetLoanNotFound() throws Exception  {
		LoanDTO loanDTO = new LoanDTO ();
		doReturn(loanDTO).when(loanservice).getLoan(1);
		 mockMvc.perform(get("/loan/view/{id}",10))
	        .andExpect(status().isNotFound());
}

	@Test
	@DisplayName("Register for loan")
	public void registerForLoan() throws Exception  {
		String url="/loan/register";
		RegisterRequest registerRequest = new RegisterRequest(1,1,10);
		RegisterResponse registerResponse = new RegisterResponse(10,22222.0);
		when(loanservice.registerCustomerForLoan(registerRequest)).thenReturn(registerResponse);
		MvcResult result = mockMvc.perform(post(url)
				.content(mapToJson(registerRequest))
				.contentType(MediaType.APPLICATION_JSON)
		).andReturn();

		String responseString = result.getResponse().getContentAsString();

		assertThat(responseString).isEqualTo(mapToJson(registerResponse));
	}



}
