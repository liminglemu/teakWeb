package com.teak.blog.config;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/2/9 16:37
 * @Project: teakWeb
 * @File: CorConfig.java
 * @Description:
 */
@Slf4j
@Configuration
public class CorConfig {
    // 提取常量
    private static final String ALLOWED_ORIGINS = "*"; // 替换为你的可信域名
    private static final String[] ALLOWED_METHODS = {"GET", "POST", "PUT", "DELETE", "OPTIONS"};
    private static final String PATH_PATTERN = "/**";

    @Bean
    public WebMvcConfigurer configurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NotNull CorsRegistry registry) {
                try {
                    registry.addMapping(PATH_PATTERN) //为所有路径启用 CORS 支持。
                            .allowedOrigins(ALLOWED_ORIGINS) //允许来自任何域名的请求。
                            .allowedMethods(ALLOWED_METHODS); //允许这些 HTTP 方法。
                } catch (Exception e) {
                    log.error("CORS配置失败", e);
                }

            }
        };
    }
}
