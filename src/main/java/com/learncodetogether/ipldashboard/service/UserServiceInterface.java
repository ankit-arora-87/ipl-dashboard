package com.learncodetogether.ipldashboard.service;

import com.learncodetogether.ipldashboard.model.User;

import java.util.List;

public interface UserServiceInterface {

    List<User> findAll();

    User findOne(Integer id);

    void save(User user);

    User deleteById(Integer id);
}
