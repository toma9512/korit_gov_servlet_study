package com.korit.korit_gov_servlet_study.ch02;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@WebServlet("/ch02/todos")
public class TodoServlet extends HttpServlet {
    private List<Todo> todos;

    @Override
    public void init() throws ServletException {
        todos = new ArrayList<>();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String title = req.getParameter("title");

        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setContentType("text/html");

        if (title==null || title.isBlank()) {
            resp.getWriter().println("[ 전체 조회 ]");
            resp.getWriter().println(todos);
            return;
        }

        List<Todo> foundTodos = todos
                .stream().filter(o -> o.getTitle().equals(title)).toList();

        if (foundTodos.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().println("해당 Todo가 없습니다.");
            return;
        }

        foundTodos.forEach(resp.getWriter()::println);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String username = req.getParameter("username");

        Todo todo = Todo.builder()
                .title(title)
                .content(content)
                .username(username)
                .build();

        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setContentType("text/html");
        Map<String, String> error = validTodo(todo);

        // 빈 값이 있는 경우
        if (!error.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            for (String key : error.keySet()) {
                resp.getWriter().println(key + error.get(key));
            }
            return;
        }

        // 빈 값이 없는 경우
        todos.add(todo);
        System.out.println(todos);
        resp.getWriter().println("사용자 등록 완료");
    }

    private Map<String, String> validTodo(Object dto) {
        Map<String, String> error = new HashMap<>();

        Arrays.stream(dto.getClass().getDeclaredFields()).forEach(f -> {
            f.setAccessible(true);
            String fieldName = f.getName();

            try {
                Object fieldValue = f.get(dto);

                if (fieldValue == null) {
                    throw new RuntimeException();
                }

                if (fieldValue.toString().isBlank()) {
                    throw new RuntimeException();
                }
            } catch (IllegalAccessException e) {
                System.out.println("필드에 접근할 수 없습니다.");
            } catch (RuntimeException e) {
                error.put(fieldName, "빈 값일 수 없습니다.");
            }
        });

        return error;
    }
}
