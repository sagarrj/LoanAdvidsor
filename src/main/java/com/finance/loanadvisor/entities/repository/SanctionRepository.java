package com.finance.loanadvisor.entities.repository;

import com.finance.loanadvisor.entities.Sanction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SanctionRepository extends JpaRepository<Sanction, Integer> {

}
