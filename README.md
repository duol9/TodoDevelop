# 일정관리 Develop # 
일정 생성, 조회, 수정, 삭제(CRUD) <br>
회원가입, 로그인, 수정, 조회, 탈퇴(CRUD)
- 필수 https://github.com/duol9/TodoDevelop/tree/required
- Lv6. https://github.com/duol9/TodoDevelop/tree/lv6

## 목적
- CRUD 구현
- JPA를 활용해 엔티티 간의 연관관계 매핑
- 쿠키와 세션, 필터로 로그인 인증/인가 설계
- 비밀번호 암호화
- Validatiion을 사용해 예외처리 및 데이터 검증 

## 개발환경
- Java17
- Spring Boot (3.4.0)
- JPA
- MySQL (8.0.28)
- Intellij IDEA

## ERD ##
<img width="488" alt="image" src="https://github.com/user-attachments/assets/2774c81e-cbd4-40cd-b4af-35420a944644" />

## API 명세서 ##

### 1. 회원가입 ###
Method
```
POST
```
URI
```
/users/signup
```
요청
```
{
    "userName":"이름",
    "email":"xx@xxx.com",
    "password":"1234"
}
```
응답
```
201 Created
회원가입 되었습니다.
```
응답2
```
409 CONFLICT
이미 존재하는 이메일입니다.
```
### 2. 로그인 ###
Method
```
POST
```
URI
```
/users/login
```
요청
```
{
    "email":"xx@naver.com",
    "password":"1234"
}
```
응답
```
200 OK
로그인 성공했습니다.
```
응답2
```
401 UNAUTHORIZED
로그인 실패
```

### 3. 유저 전체 조회 ###
Method
```
GET
```
URI
```
/users/user_list
```
응답
```
200 OK
{
    "allUser": [
        {
            "userName": "이름",
            "email": "xx@xxx.com",
            "createdAt": "2024-12-19"
        }
    ]
}
```

### 4. 유저 단건 조회 ###
Method
```
GET
```
URI
```
/users/user_list/{id}
```
응답
```
200 OK
{
    "userName": "이름",
    "email": "xx@xxx.com",
    "createAt": "2024-12-19"
}
```
응답2
```
404 NOT_FOUND
해당 id가 존재하지 않습니다.
```

### 5. 유저 정보 수정 ###
Method
```
PATCH
```
URI
```
/users/{id}
```
요청
```
{
    "userName":"수정된 이름"
}
```
응답
```
200 OK
{
    "userName": "수정된 이름",
    "email": "xx@xxx.com",
    "createAt": "2024-12-19"
}
```
응답2
```
404 NOT_FOUND
해당 id가 존재하지 않습니다.
```

### 6. 유저 삭제 ###
Method
```
DELETE
```
URI
```
/users/{id}
```
응답
```
200 OK
```
응답2
```
404 NOT_FOUND
해당 id가 존재하지 않습니다.
```

### 7. 일정 생성 ###
Method
```
POST
```
URI
```
/todos
```
요청
```
{
"title":"제목",
"contents":"할 일"
}
```
응답
```
201 Created
{
    "name": "이름",
    "title": "제목",
    "contents": "할 일",
    "createdAt": "2024-12-19",
    "modifiedAt": "2024-12-19"
}
```
응답2
```
404 NOT_FOUND
유저 조회 불가
```

### 8. 일정 단건 조회 ###
Method
```
GET
```
URI
```
/todos/{id}
```
응답
```
200 OK
{
    "name": "이름",
    "title": "제목",
    "contents": "할 일",
    "createdAt": "2024-12-19",
    "modifiedAt": "2024-12-19"
}
```
응답2
```
404 NOT_FOUND
일정 조회 불가
```

### 9. 일정 전체 조회 ###
Method
```
GET
```
URI
```
/todos
```
응답
```
200 OK
{
    "allTodos": [
        {
            "name": "이름",
            "title": "제목33",
            "contents": "할 일",
            "createdAt": "2024-12-19",
            "modifiedAt": "2024-12-19"
        }
    ]
}
```
### 10. 일정 수정 ###
Method
```
PATCH
```
URI
```
/todos/{id}
```
요청
```
{
    "contents":"할일 수정"
}
```
응답
```
200 OK
{
    "name": "이름",
    "title": "제목",
    "contents": "할일 수정",
    "createdAt": "2024-12-19",
    "modifiedAt": "2024-12-19"
}
```
응답2
```
401 UNAUTHORIZED
작성자만 수정할 수 있습니다.
```

### 11. 일정 삭제 ###
Method
```
DELETE
```
URI
```
/todos/{id}
```
응답
```
200 OK
```
응답2
```
401 UNAUTHORIZED
작성자만 수정할 수 있습니다.
```

### 12. 댓글 작성 ###
Method
```
POST
```
URI
```
/todos/{todoId}/reply
```
요청
```
{
    "comment":"댓글"
}
```
응답
```
200 OK
{
    "userName": "이름",
    "todoTitle": "제목",
    "comment": "댓글",
    "createdAt": "2024-12-19",
    "modifiedAt": "2024-12-19"
}
```

### 13. 댓글 조회 ###
Method
```
GET
```
URI
```
/todos/{todoId}/reply
```
응답
```
302 FOUND
{
    "replyEntityList": [
        {
            "userName": "이름",
            "todoTitle": "제목",
            "comment": null,
            "createdAt": "2024-12-19",
            "modifiedAt": "2024-12-19"
        }
    ]
}
```
### 14. 댓글 수정 ###
Method
```
PUT
```
URI
```
/todos/{todoId}/reply/{replyId}
```
요청
```
{
    "comment":"댓글수정"
}
```
응답
```
200 OK
{
    "userName": "이름",
    "todoTitle": "제목",
    "comment": "댓글수정",
    "createdAt": "2024-12-19",
    "modifiedAt": "2024-12-20"
}
```
응답2
```
404 NOT FOUND 
존재하지 않은 댓글입니다.
401 UNAUTHORIZED
작성자만 수정할 수 있습니다.
```

### 15. 댓글 삭제 ###
Method
```
DELETE
```
URI
```
/todos/{todoId}/reply/{replyId}
```
응답
```
200 OK
삭제 되었습니다.
```
응답2
```
404 NOT FOUND 
존재하지 않은 댓글입니다.
401 UNAUTHORIZED
작성자만 삭제할 수 있습니다.
```