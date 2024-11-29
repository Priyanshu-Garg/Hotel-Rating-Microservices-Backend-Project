package com.micro.user.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.micro.user.service.entities.Rating;
import com.micro.user.service.external.services.RatingService;

@SpringBootTest
class UserServiceApplicationTests {

	@Test
	void contextLoads() {
	}
	@Autowired
	private RatingService ratingService;
	
	@Test
	void createRating() {
		Rating rating=Rating.builder().rating(10).userId("").hotelId("").feedback("created using feign client").build();
		Rating savedRating=ratingService.createRating(rating);
		System.out.println(savedRating);
	}
}
