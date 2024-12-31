package com.example.tododevelop.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.tododevelop.filter.CustomFilter;
import com.example.tododevelop.filter.LoginFilter;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.Filter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean customFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new CustomFilter()); // 필터 등록
        filterRegistrationBean.setOrder(1); // 필터 순서1
        filterRegistrationBean.addUrlPatterns("/*"); // 전체 URL에 필터 적용

        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean loginFilter(ObjectMapper objectMapper) {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginFilter(objectMapper)); // 필터 등록
        filterRegistrationBean.setOrder(2); // 필터 순서 2
        filterRegistrationBean.addUrlPatterns("/*"); // 전체 URL에 필터 적용

        return filterRegistrationBean;
    }
}
