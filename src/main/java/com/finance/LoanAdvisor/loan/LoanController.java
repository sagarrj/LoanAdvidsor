package com.finance.LoanAdvisor.loan;


import com.finance.LoanAdvisor.customer.VO.CustomerVO;
import com.finance.LoanAdvisor.loan.VO.LoanVO;
import com.finance.LoanAdvisor.loan.VO.RegisterRequest;
import com.finance.LoanAdvisor.loan.VO.RegisterResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest){

//	@GetMapping("/register")
//	public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest) {


		RegisterResponse registerResponse = loanService.registerCustomerForLoan(registerRequest);
		return new ResponseEntity<RegisterResponse>(registerResponse, HttpStatus.OK);
	}

    /**
	 * This method accepts loan Id and returns loan details based on Id.
	 *
	 * @param id : {@link Integer}
	 * @return {@link ResponseEntity} of {@link LoanVO}
	 */
	@GetMapping(value = "/get/loan/{id}")

	public ResponseEntity<LoanVO> getLoanById(@PathVariable int id) {
		LoanVO loan = loanService.getLoan(id);
		return new ResponseEntity<LoanVO>(loan, HttpStatus.OK);
	}

	/**
	 * This method returns list of available loans

	 *
	 * @return {@link List} of {@link LoanVO}
	 */
	@GetMapping(value = "/getAllLoan")
	public List<LoanVO> getAllLoans() {
		return loanService.getAllLoan();

	}






}