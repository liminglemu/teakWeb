package com.teak.blog.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/2/23 16:02
 * @Project: teakWeb
 * @File: SnowflakeAlgorithm.java
 * @Description:
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SnowflakeAlgorithm {
}
