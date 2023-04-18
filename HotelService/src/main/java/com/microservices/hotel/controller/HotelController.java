package com.microservices.hotel.controller;


import com.microservices.hotel.entities.Hotel;
import com.microservices.hotel.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;


    @PreAuthorize("hasAnyAuthority('Admin') ")
    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel){

        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.create(hotel));


    }


    @PreAuthorize("hasAuthority('SCOPE_internal')")
    @GetMapping("/{hotelId}")
    public ResponseEntity<Hotel> get(@PathVariable String hotelId){

        return ResponseEntity.status(HttpStatus.OK).body(hotelService.get(hotelId));
    }



    @PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin')")
    @GetMapping
    public ResponseEntity<List<Hotel>> getAll(){
        return ResponseEntity.ok(hotelService.getAll());
    }

}
