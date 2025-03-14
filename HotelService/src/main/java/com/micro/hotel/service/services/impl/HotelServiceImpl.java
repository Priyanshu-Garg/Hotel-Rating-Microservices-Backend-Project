package com.micro.hotel.service.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.micro.hotel.service.entities.Hotel;
import com.micro.hotel.service.exception.ResourceNotFoundException;
import com.micro.hotel.service.repository.HotelRepository;
import com.micro.hotel.service.services.HotelService;

@Service
public class HotelServiceImpl implements HotelService{
	@Autowired
	private HotelRepository hotelRepository;

	@Override
	public Hotel create(Hotel hotel) {
		String hotelId=UUID.randomUUID().toString();
		hotel.setId(hotelId);
		return hotelRepository.save(hotel);
	}

	@Override
	public List<Hotel> getAll() {
		return hotelRepository.findAll();
	}

	@Override
	public Hotel get(String id) {
		return hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("hotel with given id not found")); 
	}

}
