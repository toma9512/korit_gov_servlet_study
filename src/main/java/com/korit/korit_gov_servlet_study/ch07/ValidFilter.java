package com.korit.korit_gov_servlet_study.ch07;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

// @WebFilter("/ch07/users")
public class ValidFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(StandardCharsets.UTF_8.name());
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        servletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());

        if ("POST".equals(httpServletRequest.getMethod())) {
            Gson gson = new GsonBuilder().create();
            SignupReqDto signupReqDto = gson.fromJson(servletRequest.getReader(), SignupReqDto.class);
            UserService userService = UserService.getInstance();
            if (userService.isNull(signupReqDto)) {
                System.out.println("빈 값이 있습니다. 다시 시도해주세요");
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
