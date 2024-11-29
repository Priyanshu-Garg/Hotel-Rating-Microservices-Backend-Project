package com.micro.user.service.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.micro.user.service.entities.Hotel;
import com.micro.user.service.entities.Rating;
import com.micro.user.service.entities.User;
import com.micro.user.service.exception.ResourceNotFoundException;
import com.micro.user.service.external.services.HotelService;
import com.micro.user.service.repository.UserRepository;
import com.micro.user.service.services.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HotelService hotelService;
	
	private Logger logger=LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public User saveUser(User user) {
	String randomUserId=UUID.randomUUID().toString();
	user.setUserId(randomUserId);
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUsers() {
		
	List<User> allUsers=userRepository.findAll();
	for(int i=0;i<allUsers.size();i++) {
	ArrayList<Rating> ratingsForUser=restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+allUsers.get(i).getUserId(),ArrayList.class);
	allUsers.get(i).setRatings(ratingsForUser);
	}
	return allUsers;
	}

	@Override
	public User getUser(String userId) {
		
		User user=userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found with this id: "+userId));
		Rating[] ratingsForUser=restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(),Rating[].class);
		logger.info("{}",ratingsForUser);
		List<Rating> ratings=Arrays.stream(ratingsForUser).toList();
	List<Rating> ratingList= ratings.stream().map(rating -> {
		Hotel hotel=hotelService.getHotel(rating.getHotelId());
//		ResponseEntity<Hotel> forEntity=restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(),Hotel.class);
//		Hotel hotel=forEntity.getBody();
//		logger.info("{}",forEntity);
		rating.setHotel(hotel);
		return rating;
		}).collect(Collectors.toList());
		user.setRatings(ratingList);
		return user;
	}

	@Override
	public User updateUser(User user) {
		
         return userRepository.save(user); 	
	}

	@Override
	public void deleteUser(String userId) {
		
		 userRepository.deleteById(userId);
	}

}
