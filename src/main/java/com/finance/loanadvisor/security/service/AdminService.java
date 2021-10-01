package com.finance.loanadvisor.security.service;

import com.finance.loanadvisor.entities.User;
import com.finance.loanadvisor.entities.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * This method authenticate user details given as input and returns boolean
	 * value by validating them.
	 * 
	 * @param user : {@link User}
	 * @return : {@link Boolean}
	 */
	public Boolean authenticate(User user) {
		User repoUser = userRepository.findByUsername(user.getUsername());
		return repoUser.getPassword().equals(user.getPassword());
	}
}
