package com.example.tododevelop.filter;

import java.io.IOException;

import org.springframework.util.PatternMatchUtils;

import com.example.tododevelop.dto.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// 로그인 필터
public class LoginFilter implements Filter {
    // 필터 적용 제외할 url
    private static final String[] WHITE_LIST = {"/", "/users/signup", "/users/login"};
    private final ObjectMapper objectMapper;

    public LoginFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain
    ) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpServletRequest.getRequestURI();

        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        log.info("로그인 필터 로직 실행");

        // 로그인 체크해야하는 URL인지 검사
        // whiteListURL에 포함되지 않은 경우 (!true => false)
        if(!isWhiteList(requestURI)) {
            // 로그인O 세션 가져옴, 없으면 null
            HttpSession session = httpServletRequest.getSession(false);

            // 로그인X
            if (session == null || session.getAttribute("userId") == null) {
                loginExceptionHandler(servletResponse);
                return;
            }

            log.info("로그인되었습니다");
         }

        // 다음 필터 있으면 실행, 없으면 controller 호출
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isWhiteList(String requestURI) {
        // request URI가 whiteListURL에 포함되는지 확인
        // 포함 true, 불포함 false
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }

    private void loginExceptionHandler(ServletResponse response) throws IOException {
        // 응답 코드 설정
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");

        // ApiResponse 객체를 JSON으로 변환
        String jsonResponse = objectMapper.writeValueAsString(
            ApiResponse.error(httpServletResponse.getStatus(), "로그인 헤주세요."));
        httpServletResponse.getWriter().write(jsonResponse);
    }
}
