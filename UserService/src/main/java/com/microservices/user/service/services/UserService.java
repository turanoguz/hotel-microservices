package com.microservices.user.service.services;

import com.microservices.user.service.entities.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    List<User> getAllUSer();

    User getUser(String userId);

}
