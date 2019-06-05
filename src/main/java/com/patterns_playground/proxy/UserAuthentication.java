package com.patterns_playground.proxy;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service	
class UserAuthentication implements Authentication {
	
	@Autowired
	UserUtil userUtil;

	private List<User> usersList;	
	private Logger logger = LoggerFactory.getLogger(UserAuthentication.class);

	@PostConstruct
	public void init() {
		usersList = userUtil.getUsersList();
	}

	@Override
	public boolean authenticate(String username, String password) {
		logger.info("authenticate() called - attempting to login");
		boolean isAuthenticated = false;
		try {
			isAuthenticated = validateUserCredentials(username, password);
		} catch (AuthenticationFailedException ex) {
			logger.info("login attempt failed. cause : {}", ex.getMessage());			
		}
		return isAuthenticated;
	}

	private Boolean validateUserCredentials(String username, String password) {
		logger.info("validateUserCredentials called with the following credentials : username {} , password {}", username, password);
		Boolean isCredentialsValid = false;
		
		Optional<User> foundUser = 
			usersList.stream()	
			.filter(e -> e.getUsername().equals(username))
			.findFirst();
		
		if(foundUser.isPresent()) {
			User user = foundUser.get();
			if(user.getPassword().equals(password)) {
				logger.info("validateUserCredentials() : Success. Credentials Validated.");
				isCredentialsValid = true;
			} else {
				logger.info("validateUserCredentials() : invalid password entered");
				throw new AuthenticationFailedException("invalid password entered.");
			}
		} else {
			logger.info("validateUserCredentials() : username not found.");
			throw new AuthenticationFailedException("username not found.");
		}		
		return isCredentialsValid;
	}

}