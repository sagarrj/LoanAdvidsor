package com.finance.LoanAdvisor.loan;

import com.finance.LoanAdvisor.entities.Sanction;
import com.finance.LoanAdvisor.repository.LoanRepository;
import com.finance.LoanAdvisor.repository.SanctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final SanctionRepository sanctionRepository;

    public Sanction registerCustomerForLoan(Integer customerId, Integer sanctionId) {

        Optional<Sanction> optionalSanction = sanctionRepository.findById(sanctionId);
        return optionalSanction.isPresent() ? optionalSanction.get() : null;

    }
}
