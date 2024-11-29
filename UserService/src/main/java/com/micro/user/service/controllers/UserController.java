package com.micro.user.service.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.micro.user.service.entities.User;
import com.micro.user.service.services.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	private Logger logger=LoggerFactory.getLogger(UserController.class);
	
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user){
		User user1=userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user1);
	}
	
	@GetMapping("/{userId}")
	@CircuitBreaker(name="ratingHotelBreaker",fallbackMethod="ratingHotelFallback")
	public ResponseEntity<User> getUser(@PathVariable String userId){
		User user=userService.getUser(userId);
		return ResponseEntity.ok(user);
	}
	
	//fallback method for circuit breaker
	public ResponseEntity<User> ratingHotelFallback(String userId,Exception ex){
		logger.info("Fallback is executed beacuse service is down:",ex.getMessage());
		User user=User.builder().email("dummy@gmail.com").name("Dummy").about("this user is dummy beacuse some service is down").userId("1234556").build();
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
	
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers(){
		List<User> allUsers=userService.getAllUsers();
		return ResponseEntity.ok(allUsers);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<User> updateUser(@RequestBody User user,@PathVariable String userId){
		
		User user1=userService.updateUser(user);
		return ResponseEntity.ok(user1);
	}
	
	@DeleteMapping("/{userId}")
	public void deleteUser( @PathVariable String userId){
		userService.deleteUser(userId);
	}

}
