package com.teak.blog.service.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/5/4 00:50
 * @Project: teakWeb
 * @File: ReportGenerateTask.java
 * @Description:
 */
@Component
@Slf4j
public class ReportGenerateTask {
    public void generateDailyReport(String params) {
        // 使用JSON工具解析params参数
        log.info("生成日报表，参数: {}", params);
    }
}
