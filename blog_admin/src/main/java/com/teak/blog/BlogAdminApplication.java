package com.teak.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The type Blog admin application.
 *
 * @author 柚mingle木
 * @version 1.0
 * @date 2023 /2/18
 */
@SpringBootApplication
@MapperScan(basePackages = "com.teak.blog.mapper")
public class BlogAdminApplication {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(BlogAdminApplication.class, args);
    }
}
