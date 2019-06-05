package com.patterns_playground.proxy;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component	
public class UserUtil {
	
	private List<User> users;
	
	@PostConstruct
	private void init() {
		users = new ArrayList<>();
		users.add(new User("Dave", "myPass1"));
		users.add(new User("John", "myPass2"));
		users.add(new User("Joe", "myPass3"));
	}
	
	public List<User> getUsersList() {
		return users;
	}

}
