package com.patterns_playground.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserAuthenicationProxy implements Authentication {

	@Autowired
	UserAuthentication userAuthentication;

	private static final int MAX_CONCURRENT_USERS = 3;
	private Integer activeUsersCount = 0;

	private Logger logger = LoggerFactory.getLogger(UserAuthenicationProxy.class);

	@Override
	public boolean authenticate(String username, String password) {
		logger.info("authenticate() called.");
		boolean isAuthenticated = false;
		try {
			isAuthenticated = checkNumOfConcurrentUsers(username, password);
		} catch(AuthenticationFailedException ex) {
			logger.info("authenticate encountered an exception : {}", ex.getMessage());
		}
		return isAuthenticated;
	}

	private boolean checkNumOfConcurrentUsers(String username, String password) {
		logger.info("validateLogin() activeUsersCount count : {}", activeUsersCount);
		if (++activeUsersCount <= MAX_CONCURRENT_USERS) {			
			return userAuthentication.authenticate(username, password);
		} else {
			throw new AuthenticationFailedException("max no. of concurrent users reached.");
		}
	}

}
