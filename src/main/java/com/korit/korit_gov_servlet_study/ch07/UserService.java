package com.korit.korit_gov_servlet_study.ch07;

import java.util.List;

public class UserService {
    private static UserService instance;
    private UserRepository userRepository;

    private UserService() {
        userRepository = UserRepository.getInstance();
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public boolean isNull(SignupReqDto signupReqDto) {
        return signupReqDto.getUsername() == null ||
                signupReqDto.getPassword() == null ||
                signupReqDto.getEmail() == null;
    }

    public boolean isDuplicatedUsername(String username) {
        return userRepository.findUserByUsername(username) != null;
    }

    public User addUser(SignupReqDto signupReqDto) {
        return userRepository.addUser(signupReqDto.toEntity());
    }

    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
}
