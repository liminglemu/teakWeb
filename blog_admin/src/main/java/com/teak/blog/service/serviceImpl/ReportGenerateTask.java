package com.teak.blog.service.serviceImpl;

import com.teak.blog.entity.model.Article;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Component;

import java.util.List;

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
    public void generateDailyReport(String s, Article params, Long l) {
        log.info("全新方法，数组，参数:{} {} {}", s, params, l);
    }
    public void generateDailyReport(String[] strings) {
        for (String string : strings) {
            log.info("数组，参数:{}", string);
        }
    }
    public void generateDailyReport(List<Article> articles) {
        for (Article article : articles) {
            log.info("数组，参数:{}", article);
        }
    }
}
