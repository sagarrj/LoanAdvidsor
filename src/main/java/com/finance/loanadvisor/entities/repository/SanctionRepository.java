package com.finance.loanadvisor.entities.repository;

import com.finance.loanadvisor.entities.Sanction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * This interface extends {@link JpaRepository} which provides JPA
 * functionalities for the entity class {@link Sanction} 
 */
@Repository
public interface SanctionRepository extends JpaRepository<Sanction, Integer> {

}
