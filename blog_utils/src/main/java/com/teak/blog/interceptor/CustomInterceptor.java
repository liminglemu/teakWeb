package com.teak.blog.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/3/8 12:56
 * @Project: teakWeb
 * @File: CustomInterceptor.java
 * @Description:
 */
@Slf4j
public class CustomInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 预处理逻辑（如参数校验、权限检查）
        log.info("请求预处理：拦截到请求 {}", request.getRequestURI());
        return true; // 返回true继续处理，false中断请求
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("请求postHandle处理：modelAndView {}", response);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("请求afterCompletion处理：拦截到请求 {}", request.getRequestURI());
    }

}
