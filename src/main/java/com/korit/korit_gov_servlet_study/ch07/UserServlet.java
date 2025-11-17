package com.korit.korit_gov_servlet_study.ch07;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/ch07/users")
public class UserServlet extends HttpServlet {
    Gson gson;
    UserService userService;

    @Override
    public void init() throws ServletException {
        gson = new GsonBuilder().create();
        userService = UserService.getInstance();
        System.out.println("Servlet 요청 들어옴");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SignupReqDto signupReqDto = gson.fromJson(req.getReader(), SignupReqDto.class);

        resp.setContentType("application/json");

        if (userService.isNull(signupReqDto)) {
            ResponseDto<User> responseDto = ResponseDto.<User>builder()
                    .message("빈 값이 있을 수 없습니다.")
                    .status(200)
                    .body(null)
                    .build();

            String json = gson.toJson(responseDto);
            resp.getWriter().write(json);
            return;
        }

        if (userService.isDuplicatedUsername(signupReqDto.getUsername())) {
            ResponseDto<User> responseDto = ResponseDto.<User>builder()
                    .message("username이 중복되었습니다.")
                    .status(200)
                    .body(null)
                    .build();
            String json = gson.toJson(responseDto);

            resp.getWriter().write(json);
            return;
        }
        User user = userService.addUser(signupReqDto);

        ResponseDto<User> responseDto = ResponseDto.<User>builder()
                .status(200)
                .message("회원가입 성공!")
                .body(user)
                .build();

        String json = gson.toJson(responseDto);
        resp.getWriter().write(json);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
                resp.setContentType("application/json");

        if (username == null) {
            ResponseDto<List<User>> responseDto = ResponseDto.<List<User>>builder()
                    .status(200)
                    .message("[ 전체 조회 ]")
                    .body(userService.getUsers())
                    .build();

            String json = gson.toJson(responseDto);

            resp.getWriter().write(json);
            return;
        }

        User foundUser = userService.findUserByUsername(username);
        if (foundUser == null) {
            ResponseDto<User> responseDto = ResponseDto.<User>builder()
                    .status(200)
                    .message("해당 username을 찾을 수 없음")
                    .body(null)
                    .build();

            String json = gson.toJson(responseDto);

            resp.getWriter().write(json);
            return;
        }

        ResponseDto<User> responseDto = ResponseDto.<User>builder()
                .status(200)
                .message("[ 단건 조회 ]")
                .body(foundUser)
                .build();

        String json = gson.toJson(responseDto);
        resp.getWriter().write(json);
    }
}
