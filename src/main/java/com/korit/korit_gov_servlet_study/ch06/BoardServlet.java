package com.korit.korit_gov_servlet_study.ch06;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/ch06/boards")
public class BoardServlet extends HttpServlet {
    private BoardRepository boardRepository;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        boardRepository = BoardRepository.getInstance();
        gson = new GsonBuilder().create();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        SuccessResponse<List<Board>> successResponse = SuccessResponse.<List<Board>>builder()
                .message("[ 전체 조회 ]")
                .body(boardRepository.getBoards())
                .build();

        String json = gson.toJson(successResponse);

        resp.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BoardReqDto boardReqDto = gson.fromJson(req.getReader(), BoardReqDto.class);

        Board board = boardRepository.addBoard(boardReqDto.toEntity());

        SuccessResponse<Board> successResponse = SuccessResponse.<Board>builder()
                .message("게시글 작성 완료")
                .body(board)
                .build();

        String json = gson.toJson(successResponse);

        resp.setContentType("application/json");

        resp.getWriter().write(json);
    }
}
