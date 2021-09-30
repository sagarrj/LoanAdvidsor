package com.finance.LoanAdvisor.loan;


import com.finance.LoanAdvisor.config.BadRequestException;
import com.finance.LoanAdvisor.config.DataNotFoundException;
import com.finance.LoanAdvisor.config.LoanConstants;
import com.finance.LoanAdvisor.customer.dto.CustomerDTO;
import com.finance.LoanAdvisor.loan.DTO.LoanDTO;
import com.finance.LoanAdvisor.loan.DTO.RegisterRequest;
import com.finance.LoanAdvisor.loan.DTO.RegisterResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * @author pkhedkar
 *
 */
@RestController
@RequestMapping("/loan")
@RequiredArgsConstructor
public class LoanController {

	Logger logger = LoggerFactory.getLogger(LoanController.class);
	private final LoanService loanService;

	/**
	 * @param registerRequest
	 * @return
	 */
	@PostMapping("/register")
	public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest) {

//	@GetMapping("/register")
//	public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest) {

		RegisterResponse registerResponse = loanService.registerCustomerForLoan(registerRequest);
		return new ResponseEntity<RegisterResponse>(registerResponse, HttpStatus.OK);
	}

	/**
	 * 
	 * This method accepts loan Id and returns loan details based on Id.
	 *
	 * @param id : {@link Integer}
	 * @return {@link ResponseEntity} of {@link LoanDTO}
	 */
	@GetMapping(value = "/view/{id}")

	public ResponseEntity<LoanDTO> getLoan(
			@PathVariable("id") @NotNull(message = "Loan Id should not be empty") Integer id)
			throws DataNotFoundException, BadRequestException {
		LoanDTO loanDTO = loanService.getLoan(id);
		return new ResponseEntity<LoanDTO>(loanDTO, HttpStatus.OK);

	}

	/**
	 * This method returns list of available loans
	 * 
	 * @return {@link List} of {@link LoanDTO}
	 * @return {@link List} of {@link LoanVO}
	 * 
	 */
	@GetMapping(value = "/list")
	public ResponseEntity<List<LoanDTO>> getAllLoans() {
		List<LoanDTO> loanDTOList = loanService.getAllLoan();
		logger.info("List of customers from controller");
		return new ResponseEntity<List<LoanDTO>>(loanDTOList, HttpStatus.OK);

	}

}
