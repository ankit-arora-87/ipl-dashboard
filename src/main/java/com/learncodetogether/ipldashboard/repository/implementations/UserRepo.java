package com.learncodetogether.ipldashboard.repository.implementations;

import com.learncodetogether.ipldashboard.model.User;
import com.learncodetogether.ipldashboard.repository.UserRepoInterface;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class UserRepo implements UserRepoInterface {

    private static Integer userCount = 3;
    private static List<User> users = new ArrayList<>();

    static {
        try {
            users.add(
                    new User(
                            1,
                            "Ankit Arora",
                            new SimpleDateFormat("dd-MM-yyyy").parse("27-08-1987")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            users.add(
                    new User(
                            2,
                            "Jagdeep Kaur",
                            new SimpleDateFormat("dd-MM-yyyy").parse("25-02-1987")
                    )
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            users.add(
                    new User(
                            3,
                            "Revan Arora",
                            new SimpleDateFormat("dd-MM-yyyy").parse("23-04-2016")
                    )
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public User findOne(Integer id) {
        return users.stream().filter(user -> {
            return user.getId() == id;
        }).findFirst().orElse(null);
    }

    @Override
    public void save(User user) {
        if(user.getId() == null){
            user.setId(++userCount);
        }
        users.add(user);
    }

    @Override
    public User deleteById(Integer id) {
        Iterator<User> userIterator = users.iterator();
        while(userIterator.hasNext()){
            User user = userIterator.next();
            if(user.getId() == id){
                userIterator.remove();
                return user;
            }
        }
        return null;
    }
}
