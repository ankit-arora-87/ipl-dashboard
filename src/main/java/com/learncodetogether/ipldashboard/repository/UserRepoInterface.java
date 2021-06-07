package com.learncodetogether.ipldashboard.repository;

import com.learncodetogether.ipldashboard.model.User;

import java.util.List;

public interface UserRepoInterface {

    List<User> findAll();

    User findOne(Integer id);

    void save(User user);

    User deleteById(Integer id);
}
