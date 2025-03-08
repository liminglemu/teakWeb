package com.teak.blog.config;

import com.teak.blog.filter.CustomFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/3/8 13:00
 * @Project: teakWeb
 * @File: FilterConfig.java
 * @Description:
 */
@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<CustomFilter> loggingFilter() {
        FilterRegistrationBean<CustomFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CustomFilter());
        registrationBean.addUrlPatterns("/*"); // 拦截所有请求
        return registrationBean;
    }
}
