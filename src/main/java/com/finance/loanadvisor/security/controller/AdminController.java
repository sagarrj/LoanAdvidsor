package com.finance.loanadvisor.security.controller;

import com.finance.loanadvisor.entities.User;
import com.finance.loanadvisor.exception.ApplicationException;
import com.finance.loanadvisor.security.JwtUtil;
import com.finance.loanadvisor.security.dto.AuthResponse;
import com.finance.loanadvisor.security.service.AdminService;
import com.finance.loanadvisor.security.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController
{
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;


    @Autowired
    private AdminService adminService;


    @PostMapping("/authenticate")
    public @ResponseBody
    AuthResponse authenticate(@RequestBody User user) throws ApplicationException {

        try {
            authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new ApplicationException("Incorrect username or password", e);
        }


    final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
    final String jwt = jwtTokenUtil.generateToken(userDetails);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    return new AuthResponse(jwt,roles);

    }
}