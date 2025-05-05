package com.teak.blog.filter;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/3/8 12:59
 * @Project: teakWeb
 * @File: CustomFilter.java
 * @Description:
 */
@Slf4j
public class CustomFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
        // 在响应发送前修改内容
//        httpResponse.setHeader("X-Custom-Header", "CustomValue");
        log.info("请求处理：执行过滤器逻辑");
        chain.doFilter(request, response); // 继续执行后续过滤器或控制器

        // 可在此处处理响应体（需包装响应流）
    }
}
