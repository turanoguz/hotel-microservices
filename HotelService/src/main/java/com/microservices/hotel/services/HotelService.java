package com.microservices.hotel.services;

import com.microservices.hotel.entities.Hotel;

import java.util.List;

public interface HotelService {

    Hotel create(Hotel hotel);

    List<Hotel> getAll();

    Hotel get(String id);

}
