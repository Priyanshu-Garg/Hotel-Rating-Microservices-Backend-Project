package com.micro.rating.service.services;

import java.util.List;

import com.micro.rating.service.entities.Rating;

public interface RatingService {

	Rating createRating(Rating rating);
	List<Rating> getAll();
	List<Rating> getRatingByUserId(String userId);
	List<Rating> getRatingByHotelId(String hotelId);
}
