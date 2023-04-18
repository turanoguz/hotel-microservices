package com.microservices.user.service.controller;

import com.microservices.user.service.entities.User;
import com.microservices.user.service.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User user1 = userService.saveUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(user1);

    }



    //@CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallBack")
    //@Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallBack")
    @GetMapping("/{userId}")
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallBack")
    public  ResponseEntity<User> getSingleUser(@PathVariable String userId){


        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);

    }

    public ResponseEntity<User> ratingHotelFallBack(String userId, Exception ex){

        User user = User.builder().email("oguz001@gmail.com")
                .name("oguz")
                .about("bu kullanıcı yenıı")
                .userId("123456")
                .build();
        return new ResponseEntity<>(user,HttpStatus.BAD_REQUEST);

    }

    @GetMapping
    public  ResponseEntity<List<User>> getAllUser(){

        List<User> allUSer = userService.getAllUSer();
        return ResponseEntity.ok(allUSer);


    }
}
