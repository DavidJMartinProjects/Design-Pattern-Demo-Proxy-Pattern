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
	public Boolean authenticate(String username, String password) {
		logger.info("authenticate() called.");
		boolean isAuthenticated = false;
		try {
			isAuthenticated = checkNumOfConcurrentUsers(username, password);
		} catch(AuthenticationFailedException ex) {
			logger.info("authenticate encountered an exception : {}", ex.getMessage());
		}
		return isAuthenticated;
	}

	private Boolean checkNumOfConcurrentUsers(String username, String password) {
		logger.info("validateLogin() activeUsersCount count : {}", activeUsersCount);
		int userCount = activeUsersCount;
		boolean isValidLogin = false;
		if (++userCount <= MAX_CONCURRENT_USERS) {
			if(userAuthentication.authenticate(username, password)) {
				activeUsersCount++;
				isValidLogin = true;
			}			
		} else {
			throw new AuthenticationFailedException("max no. of concurrent users reached.");
		}
		return isValidLogin;
	}

}
