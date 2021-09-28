package com.finance.LoanAdvisor.entities.repository;

import com.finance.LoanAdvisor.entities.Borrower;
import com.finance.LoanAdvisor.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower,Integer> {

    List<Borrower> findByCustomer_customerIdAndSanction_sanctionId(Integer customerId, Integer sanctionId);
}
