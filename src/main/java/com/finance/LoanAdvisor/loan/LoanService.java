package com.finance.LoanAdvisor.loan;

import com.finance.LoanAdvisor.config.ApplicationException;
import com.finance.LoanAdvisor.config.DataNotFoundException;
import com.finance.LoanAdvisor.config.LoanConstants;
import com.finance.LoanAdvisor.entities.Borrower;
import com.finance.LoanAdvisor.entities.Customer;
import com.finance.LoanAdvisor.entities.Loan;
import com.finance.LoanAdvisor.entities.Sanction;
import com.finance.LoanAdvisor.entities.repository.BorrowerRepository;
import com.finance.LoanAdvisor.entities.repository.CustomerRepository;
import com.finance.LoanAdvisor.entities.repository.LoanRepository;
import com.finance.LoanAdvisor.entities.repository.SanctionRepository;
import com.finance.LoanAdvisor.loan.VO.LoanVO;
import com.finance.LoanAdvisor.loan.VO.RegisterRequest;
import com.finance.LoanAdvisor.loan.VO.RegisterResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import com.finance.LoanAdvisor.entities.LoanType;

import java.util.Optional;

import static com.finance.LoanAdvisor.config.LoanConstants.*;

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
        if(optionalCustomer.isPresent() && optionalSanction.isPresent()){
            Customer customer = optionalCustomer.get();
            Sanction sanction = optionalSanction.get();

            Integer maxTenure = MAX_AGE - customer.getAge();
            if(maxTenure < 1){
                throw new ApplicationException(CANNOT_PROVIDE_LOAN_FOR_SUCH_SMALL_DURATION + maxTenure + " Max age is "+ MAX_AGE);
            }
            Integer tenure = registerRequest.getPreferredTenure() < maxTenure ?
                    registerRequest.getPreferredTenure() : maxTenure;
            Double emi = getEMI(sanction.getROI(),tenure,sanction.getLoanAmount());

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

        }else{
            throw new DataNotFoundException(CUSTOMER_SANCTION_NOT_FOUND);
        }

    }
// Get Loan By Id
	public LoanVO getLoan(int id) throws DataNotFoundException{

		Loan loan=loanRepository.findById(id).orElse(null);
		if(loan==null) {
			logger.warn("Loan not found");
			throw new DataNotFoundException("Loan not found");
		}
		logger.info("Loan returned from service");
		LoanVO loanVOS = convertToLoanVO(loan);
		return loanVOS;
		

	}

//Get All List Of Loan
	public List<LoanVO> getAllLoan() throws DataNotFoundException{
		
        List<Loan> loans = loanRepository.findAllByStatus('A');
		if(loans.isEmpty()) {
			logger.warn("List is empty");
			throw new DataNotFoundException("List is empty");
		}
		logger.info("List of Loans from service");
		List<LoanVO> loanVOS = convertToLoanVOList(loans);
        return loanVOS;
       
		
	
	}

    private List<LoanVO> convertToLoanVOList(List<Loan> loans) {
       List<LoanVO> loanVOS = new ArrayList<>();
        
            	LoanVO loanVO = new LoanVO();
            	for(Loan loan:loans) {
            	
            		 loanVOS.add(convertToLoanVO(loan));
            			}
            			
                   return	loanVOS;
            	}
    

    
 
    private LoanVO convertToLoanVO(Loan loan) {

        LoanVO loanVO = new  LoanVO();
    	  loanVO.setLoanId(loan.getLoanId());;
    	  loanVO.setLoanDesc(loan.getLoanDesc());
  		  loanVO.setLoanType(loan.getLoanType().getLoanDesc());
  		  loanVO.setROI(loan.getROI());
           return  loanVO;
    }

    



    public Double getEMI(Double rate, Integer tenure, Double principal){
        Double emi;

        rate = rate / (12 * 100); // one month interest
        tenure = tenure * 12; // one month period
        emi = (principal * rate * (float)Math.pow(1 + rate, tenure))
                / (float)(Math.pow(1 + rate, tenure) - 1);

        return (double) Math.round(emi);
    }
    


	

}
