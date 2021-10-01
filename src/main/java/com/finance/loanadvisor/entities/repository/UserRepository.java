package com.finance.loanadvisor.entities.repository;

import com.finance.loanadvisor.entities.Borrower;
import com.finance.loanadvisor.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * This interface extends {@link JpaRepository} which provides JPA
 * functionalities for the entity class {@link Borrower} and contain custom
 * method such as findByUsername.
 */
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    public User findByUsername(String username);
}
