package com.patterns_playground.proxy;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;	

@Component
public class AppDemo implements CommandLineRunner {	
	
	@Autowired
	UserAuthenicationProxy userAuthenicationProxy;
	
	Logger logger = LoggerFactory.getLogger(AppDemo.class);

	private List<User> userLogins;

	@PostConstruct
	private void init() {
		userLogins = new ArrayList<>();
		userLogins.add(new User("Dave", "myPass1"));
		userLogins.add(new User("James", "myPass2"));
		userLogins.add(new User("John", "myPass3"));
		userLogins.add(new User("Jason", "myPass4"));
		userLogins.add(new User("Joe", "myPass5"));
		userLogins.add(new User("Steve", "myPass6"));
		userLogins.add(new User("Andy", "myPass7"));
	}
	
	public void proxyPatternDemo() {		
		for (User user : userLogins) {
			logger.info("calling with user info : {}", user);
			userAuthenicationProxy.authenticate(user.getUsername(), user.getPassword());
		}
	}

	@Override
	public void run(String... args) throws InterruptedException {
		proxyPatternDemo();		
	}

}
