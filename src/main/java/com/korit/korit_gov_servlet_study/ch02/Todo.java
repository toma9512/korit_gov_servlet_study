package com.korit.korit_gov_servlet_study.ch02;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Todo {
    private String title;
    private String content;
    private String username;
}

/*
List에 투두들 저장
저장 요청 시 쿼리파라미터에서 값을 가져와서 리스트에 저장
저장 전에 3가지 필드가 다 채워져 있는지 확인 (reflection)
빈값있으면 map에 필드명과 빈 값일 수 없습니다 넣고 응답(400) bad request

조회 요청 시 쿼리파라미터가 없으면 전체 조회
있으면 title로 단건 조회
해당 title 투두가 없으면 "해당 투두가 없습니다" 응답(404) not found
 */