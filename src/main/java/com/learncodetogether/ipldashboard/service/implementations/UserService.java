package com.learncodetogether.ipldashboard.service.implementations;

import com.learncodetogether.ipldashboard.model.User;
import com.learncodetogether.ipldashboard.repository.UserRepoInterface;
import com.learncodetogether.ipldashboard.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService implements UserServiceInterface {

    @Autowired
    private UserRepoInterface userRepoInterface;

    @Override
    public List<User> findAll() {
        return userRepoInterface.findAll();
    }

    @Override
    public User findOne(Integer id) {
        return userRepoInterface.findOne(id);
    }

    @Override
    public void save(User user) {
        userRepoInterface.save(user);
    }

    @Override
    public User deleteById(Integer id){
        return userRepoInterface.deleteById(id);
    }
}
