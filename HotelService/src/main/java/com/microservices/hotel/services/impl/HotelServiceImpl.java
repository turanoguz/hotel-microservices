package com.microservices.hotel.services.impl;

import com.microservices.hotel.entities.Hotel;
import com.microservices.hotel.repositories.HotelRepository;
import com.microservices.hotel.services.HotelService;
import com.microservices.hotel.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;


    @Override
    public Hotel create(Hotel hotel) {
        String hotelId = UUID.randomUUID().toString();
        hotel.setId(hotelId);

        return this.hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getAll() {
        return this.hotelRepository.findAll();
    }

    @Override
    public Hotel get(String id) {
        return this.hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("hotel with given id not found!"));
    }
}
