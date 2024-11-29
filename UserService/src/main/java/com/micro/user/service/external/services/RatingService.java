package com.micro.user.service.external.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.micro.user.service.entities.Rating;

@FeignClient(name="RATING-SERVICE")
@Service
public interface RatingService {

	@PostMapping("/ratings")
	Rating createRating(Rating values);
	@PutMapping("/ratings/{ratingId}")
	Rating updateRating(@PathVariable String ratingId,Rating rating);
	@DeleteMapping("/ratings/{ratingId}")
	void deleteRating(@PathVariable String ratingId);
}
