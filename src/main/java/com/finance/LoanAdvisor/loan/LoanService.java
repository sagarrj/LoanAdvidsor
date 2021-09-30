package com.finance.LoanAdvisor.loan;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finance.LoanAdvisor.config.ApplicationException;
import com.finance.LoanAdvisor.config.DataNotFoundException;
import com.finance.LoanAdvisor.entities.Borrower;
import com.finance.LoanAdvisor.entities.Customer;
import com.finance.LoanAdvisor.entities.Loan;
import com.finance.LoanAdvisor.entities.Sanction;
import com.finance.LoanAdvisor.entities.repository.BorrowerRepository;
import com.finance.LoanAdvisor.entities.repository.CustomerRepository;
import com.finance.LoanAdvisor.entities.repository.LoanRepository;
import com.finance.LoanAdvisor.entities.repository.SanctionRepository;
import com.finance.LoanAdvisor.loan.DTO.LoanDTO;
import com.finance.LoanAdvisor.loan.DTO.RegisterRequest;
import com.finance.LoanAdvisor.loan.DTO.RegisterResponse;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static com.finance.LoanAdvisor.config.LoanConstants.*;

/**
 * @author pkhedkar
 *
 */
@Service
@RequiredArgsConstructor
public class LoanService {

	Logger logger = LoggerFactory.getLogger(LoanController.class);

	private final LoanRepository loanRepository;
	private final SanctionRepository sanctionRepository;
	private final CustomerRepository customerRepository;
	private final BorrowerRepository borrowerRepository;

	public RegisterResponse registerCustomerForLoan(RegisterRequest registerRequest) {

		Optional<Customer> optionalCustomer = customerRepository.findById(registerRequest.getCustomerId());
		Optional<Sanction> optionalSanction = sanctionRepository.findById(registerRequest.getSanctionId());
		if (optionalCustomer.isPresent() && optionalSanction.isPresent()) {
			Customer customer = optionalCustomer.get();
			Sanction sanction = optionalSanction.get();

			Integer maxTenure = MAX_AGE - customer.getAge();
			if (maxTenure < 1) {
				throw new ApplicationException(
						CANNOT_PROVIDE_LOAN_FOR_SUCH_SMALL_DURATION + maxTenure + " Max age is " + MAX_AGE);
			}
			Integer tenure = registerRequest.getPreferredTenure() < maxTenure ? registerRequest.getPreferredTenure()
					: maxTenure;
			Double emi = getEMI(sanction.getROI(), tenure, sanction.getLoanAmount());

			Borrower borrower = new Borrower();
			borrower.setCustomer(customer);
			borrower.setSanction(sanction);
			borrower.setTenure(tenure);
			borrower.setEmi(emi);
			borrower.setStatus('A');
			borrower.setCreateDttm(new Date());
			Borrower save = borrowerRepository.save(borrower);

			RegisterResponse registerResponse = new RegisterResponse();
			registerResponse.setEmi(emi);
			registerResponse.setTenure(tenure);
			return registerResponse;

		} else {
			throw new DataNotFoundException(CUSTOMER_SANCTION_NOT_FOUND);
		}

	}

	/**
	 * This method accepts loan Id and returns loan details based on Id
	 * @param id:{@link Integer}
	 * @return {@link LoanDTO}
	 * @throws DataNotFoundException
	 */
	public LoanDTO getLoan(int id) throws DataNotFoundException {

		Optional<Loan> optionalLoan = loanRepository.findById(id);
//Optional.ofNullable(optionalLoan)!=null
	
		if (!optionalLoan.isPresent()||Optional.ofNullable(optionalLoan)==null)
		{
			logger.warn("Loan not found");
			throw new DataNotFoundException(LOAN_NOT_FOUND);
		}
		
		Loan loan = optionalLoan.get();
		logger.info("Loan returned from service");
		LoanDTO loanVOS = convertToLoanDTO(loan);
		return loanVOS;
		
		
		
	}

	/**
	 * This method returns list of all available Loan
	 * 
	 * @return {@link LoanDTO}
	 * @throws DataNotFoundException
	 */
	public List<LoanDTO> getAllLoan() throws DataNotFoundException {

		List<Loan> loans = loanRepository.findAllByStatus(ACTIVE);
		if (loans.isEmpty()) {
			logger.warn("List is empty");
			throw new DataNotFoundException(LIST_IS_EMPTY);
		}
		logger.info("List of Loans from service");
		List<LoanDTO> loanDTO = convertToLoanDTOList(loans);
		return loanDTO;
		

	}

	/**
<<<<<<< HEAD
	 * This method converts List {@link loan} object into {@link LoanDTO} object
	 * 
	 * @param loans:{@link loan}
	 * @return {@link LoanDTO}
=======
	 * This method converts List {@link Loan} object into {@link LoanVO} object
	 * 
	 * @param loans:{@link Loan}
	 * @return {@link LoanVO}
>>>>>>> cf208cca6162a7c3cfe518188ac9e34eca4d641e
	 */
	private List<LoanDTO> convertToLoanDTOList(List<Loan> loans) {
		List<LoanDTO> loanDTO = new ArrayList<>();

		LoanDTO loanDTOS = new LoanDTO();
		for (Loan loan : loans) {

			loanDTO.add(convertToLoanDTO(loan));
		}

		return loanDTO;
	}


	private LoanDTO convertToLoanDTO(Loan loan) {
//	      ObjectMapper mapper = new ObjectMapper();
//	      LoanDTO loanDTO= new LoanDTO();
//	      loanDTO.setLoanId(loan.getLoanId());
//	      loanDTO.setLoanDesc(loan.getLoanDesc());
//	      loanDTO.setLoanType(loan.getLoanType().getLoanDesc());
//	      loanDTO.setROI(loan.getROI());
//	      //map json to student
//	      try{
//	    	   LoanDTO loanDTOS = mapper.readValues(loanDTO, LoanDTO.class);
//	         
//	       //  System.out.println(loanDTOs);
//	         
//	         loanDTO = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(loanDTO);
//	         
//	      return loanDTOS;
//	      }
//	      catch (JsonParseException e) { e.printStackTrace();}
//	      catch (JsonMappingException e) { e.printStackTrace(); }
//	      catch (IOException e) { e.printStackTrace(); }
//	      
	      
	      
		LoanDTO loanDTO = new LoanDTO();
		loanDTO.setLoanId(loan.getLoanId());
		loanDTO.setLoanDesc(loan.getLoanDesc());
		loanDTO.setLoanType(loan.getLoanType().getLoanDescription());
		loanDTO.setROI(loan.getROI());
		return loanDTO;

	

	}

	public Double getEMI(Double rate, Integer tenure, Double principal) {
		Double emi;

		rate = rate / (12 * 100); // one month interest
		tenure = tenure * 12; // one month period
		emi = (principal * rate * (float) Math.pow(1 + rate, tenure)) / (float) (Math.pow(1 + rate, tenure) - 1);

		return (double) Math.round(emi);
	}

}
