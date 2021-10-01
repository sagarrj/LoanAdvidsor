package com.finance.loanadvisorr.entities.repository;

import com.finance.loanadvisorr.entities.Sanction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SanctionRepository extends JpaRepository<Sanction, Integer> {

}
