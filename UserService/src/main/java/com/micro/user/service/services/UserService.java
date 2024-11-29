package com.micro.user.service.services;

import java.util.List;

import com.micro.user.service.entities.User;

public interface UserService {
	
	User saveUser(User user);
	List<User> getAllUsers();
	User getUser(String userId);
	User updateUser(User user);
	void deleteUser(String userId);
	
}
