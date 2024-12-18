package com.example.tododevelop.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
// 로그인 필터
public class LoginFilter implements Filter {
    // 필터 적용 제외할 url
    private static final String[] WHITE_LIST = {"/", "/users/signup", "/users/login"};

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain
    ) throws IOException, ServletException {

        // 다양한 기능을 쓰기 위해 다운캐스팅 후 필터가 작동될 uri get
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
                throw new RuntimeException("로그인 해주세요.");
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
}
