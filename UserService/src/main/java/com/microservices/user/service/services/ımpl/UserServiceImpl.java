package com.microservices.user.service.services.ımpl;

import com.microservices.user.service.entities.Hotel;
import com.microservices.user.service.entities.Rating;
import com.microservices.user.service.entities.User;
import com.microservices.user.service.exceptions.ResourceNotFoundException;
import com.microservices.user.service.external.services.HotelService;
import com.microservices.user.service.repositories.UserRepository;
import com.microservices.user.service.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;


    @Override
    public User saveUser(User user) {

        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);

        return this.userRepository.save(user);
    }

    @Override
    public List<User> getAllUSer() {
        return this.userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        log.warn("BURAYA GELMİYOR");

        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user with given id is not found on server !"));

        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);
        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();

        List<Rating> ratingList = ratings.stream().map(rating -> {
            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
            Hotel hotel = hotelService.getHotel(rating.getHotelId());


            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingList);
        return user;


    }
}
