package com.finance.LoanAdvisor.security.service;

import com.finance.LoanAdvisor.entities.User;
import com.finance.LoanAdvisor.entities.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService
{

    @Autowired
    private UserRepository userRepository;

    public Boolean authenticate(User user) {
        User repoUser = userRepository.findByUsername(user.getUsername());
        return repoUser.getPassword().equals(user.getPassword());
    }
}
