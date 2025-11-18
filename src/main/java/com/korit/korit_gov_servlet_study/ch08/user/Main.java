package com.korit.korit_gov_servlet_study.ch08.user;

import com.korit.korit_gov_servlet_study.ch08.user.dto.SignupReqDto;
import com.korit.korit_gov_servlet_study.ch08.user.entity.User;
import com.korit.korit_gov_servlet_study.ch08.user.service.UserService;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        UserService userService = UserService.getInstance();
        // SignupReqDto signupReqDto = SignupReqDto.builder()
        //         .username("dfs39326848")
        //         .password("sds")
        //         .age(24)
        //         .build();
        // User user = userService.addUser(signupReqDto);
        // System.out.println(user);
        System.out.println(userService.findUserByUsername("df"));
    }
}
