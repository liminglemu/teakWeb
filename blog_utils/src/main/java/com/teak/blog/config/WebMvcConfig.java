package com.teak.blog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.teak.blog.converter.LongToStringConverter;
import com.teak.blog.converter.SimpleDateFormatConverter;
import com.teak.blog.interceptor.CustomInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Date;
import java.util.List;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/3/8 12:54
 * @Project: teakWeb
 * @File: WebMvcConfig.java
 * @Description:
 */
@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CustomInterceptor())
                .addPathPatterns("/**");// 拦截所有请求
//                .excludePathPatterns("/login"); // 排除登录接口
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Long.class, new LongToStringConverter());

        /*对Date数据单独进行转换处理，同时去除yml文件中的json全局转换格式*/
        module.addSerializer(Date.class, new SimpleDateFormatConverter());

        objectMapper.registerModule(module);
        converter.setObjectMapper(objectMapper);
        converters.add(0, converter);
    }
}
