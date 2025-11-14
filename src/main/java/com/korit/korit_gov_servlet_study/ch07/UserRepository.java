package com.korit.korit_gov_servlet_study.ch07;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static UserRepository instance;
    List<User> users;
    private Integer userId = 1;

    private UserRepository() {
        users = new ArrayList<>();
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public User addUser(User user) {
        user.setUserId(userId++);
        users.add(user);
        return user;
    }

    public List<User> getUsers() {
        return users;
    }

    public User findUserByUsername(String username) {
        List<User> foundUser = users.stream().filter(o -> o.getUsername().equals(username)).toList();
        if (foundUser.isEmpty()) return null;
        return foundUser.get(0);
    }
}
