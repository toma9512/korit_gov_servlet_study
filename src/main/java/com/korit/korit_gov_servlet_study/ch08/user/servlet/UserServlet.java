package com.korit.korit_gov_servlet_study.ch08.user.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.korit.korit_gov_servlet_study.ch08.user.dto.ApiRespDto;
import com.korit.korit_gov_servlet_study.ch08.user.dto.SignupReqDto;
import com.korit.korit_gov_servlet_study.ch08.user.entity.User;
import com.korit.korit_gov_servlet_study.ch08.user.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/ch08/users")
public class UserServlet extends HttpServlet {
    private UserService userService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        userService = UserService.getInstance();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SignupReqDto signupReqDto = objectMapper.readValue(req.getReader(), SignupReqDto.class);

        resp.setContentType("application/json");

        if (userService.isDuplicatedUsername(signupReqDto.getUsername())) {
            ApiRespDto<String> apiRespDto = ApiRespDto.<String>builder()
                    .message("중복된 username입니다.")
                    .status("failed")
                    .body(signupReqDto.getUsername())
                    .build();

            objectMapper.writeValue(resp.getWriter(), apiRespDto);
            return;
        }

        User user = userService.addUser(signupReqDto);

        if (user == null) {
            ApiRespDto<String> apiRespDto = ApiRespDto.<String>builder()
                    .message("회원 가입 실패")
                    .status("failed")
                    .body("회원 가입 실패")
                    .build();
            objectMapper.writeValue(resp.getWriter(), apiRespDto);
            return;
        }

        ApiRespDto<User> apiRespDto = ApiRespDto.<User>builder()
                .message("회원 가입 성공")
                .status("success")
                .body(user)
                .build();

        objectMapper.writeValue(resp.getWriter(), apiRespDto);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String keyword = req.getParameter("keyword");
        ApiRespDto<?> apiRespDto;

        if (username != null) {
            User user = userService.findUserByUsername(username);
            if (user == null) {
                apiRespDto = ApiRespDto.<String>builder()
                        .message("찾으려는 username이 없습니다.")
                        .status("success")
                        .body(username)
                        .build();
            } else {
                apiRespDto = ApiRespDto.<User>builder()
                        .message("[ 유저 조회 ]")
                        .status("success")
                        .body(user)
                        .build();
            }
        } else if (keyword != null) {
            List<User> users = userService.getAllUserList();
            if (users.isEmpty()) {
                apiRespDto = ApiRespDto.<String>builder()
                        .message("찾으려는 username이 없습니다")
                        .status("success")
                        .body(keyword)
                        .build();
            } else {
                apiRespDto = ApiRespDto.<List<User>>builder()
                        .message("[ keyword 조회 ]")
                        .status("success")
                        .body(users)
                        .build();
            }
        } else {
            apiRespDto = ApiRespDto.<List<User>>builder()
                    .message("[ 유저 전체 조회 ]")
                    .status("success")
                    .body(userService.getAllUserList())
                    .build();
        }

        resp.setContentType("application/json");

        objectMapper.writeValue(resp.getWriter(), apiRespDto);
    }
}
