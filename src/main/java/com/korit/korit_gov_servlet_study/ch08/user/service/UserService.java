package com.korit.korit_gov_servlet_study.ch08.user.service;

import com.korit.korit_gov_servlet_study.ch08.user.dao.UserDao;
import com.korit.korit_gov_servlet_study.ch08.user.dto.SignupReqDto;
import com.korit.korit_gov_servlet_study.ch08.user.entity.User;

import java.util.List;
import java.util.Optional;

public class UserService {
    private static UserService instance;
    private UserDao userDao;

    private UserService() {
        userDao = UserDao.getInstance();
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public boolean isDuplicatedUsername(String username) {
        return userDao.findUserByUsername(username).isPresent();
    }

    public User findUserByUsername(String username) {
        Optional<User> foundUser = userDao.findUserByUsername(username);
        if (foundUser.isPresent()) return foundUser.get();
        return null;
    }

    public User addUser(SignupReqDto signupReqDto) {
        return userDao.addUser(signupReqDto.toEntity());
    }

    public List<User> getAllUserList() {
        return userDao.getAllUserList();
    }

    public List<User> getUserByKeyword(String keyword) {
        return userDao.findUserByKeyword(keyword);
    }
}
