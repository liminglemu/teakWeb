package com.teak.blog.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/3/1 01:46
 * @Project: teakWeb
 * @File: OssConfig.java
 * @Description:
 */
@Slf4j
@Configuration
@Setter
@Getter
@ConfigurationProperties(prefix = "oss")
public class OssConfig {

    private String endpoint;

    private String bucketName;


    @Bean
    public OSS ossClient() {
        log.info("✅ 初始化Oss");
        log.info("endpoint:{}", endpoint);
        log.info("bucketName:{}", bucketName);
        return new OSSClientBuilder().build(endpoint, "LTAI5t8tbEDxXwWupFqPaBEj", "ETendtTuR2F84iZPvEeJDbChDr428C");
    }
}
