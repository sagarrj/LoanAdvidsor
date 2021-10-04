package com.finance.loanadvisor.security.user;

import com.finance.loanadvisor.entities.User;
import com.finance.loanadvisor.entities.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * This service class contain business logic implementation of {@link User}
 * class.
 *
 */
@Service
//@RequiredArgsConstructor
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * This method accepts and saves user details and return saved user object.
	 * 
	 * @param user : {@link User}
	 * @return : {@link User}
	 */
	public User createUser(User user) {
		user.setCreateDttm(new Date());
		user.setCreatedBy(0);
		String password = passwordEncoder.encode(user.getPassword());
		user.setPassword(password);
		User u = userRepository.save(user);

		return u;
	}

	public User fetchUser(String username) {
		return userRepository.findByUsername(username);
	}

	public void fetchUserRole(String username) {
	}
}
