package com.finance.LoanAdvisor.entities.repository;

import com.finance.LoanAdvisor.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    public User findByUsername(String username);
}
