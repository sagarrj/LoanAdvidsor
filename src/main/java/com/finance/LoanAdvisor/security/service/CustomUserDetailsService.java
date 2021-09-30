package com.finance.LoanAdvisor.security.service;

import com.finance.LoanAdvisor.security.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("CustomUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService
{
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) {

        com.finance.LoanAdvisor.entities.User dbUser = userService.fetchUser(username);

        List<GrantedAuthority> roles = new ArrayList<>();

         return new User(dbUser.getUsername(), dbUser.getPassword(),
                 roles);
    }
}
