package com.finance.LoanAdvisor.repository;

import com.finance.LoanAdvisor.entities.Sanction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SanctionRepository extends JpaRepository<Sanction, Integer> {

}
