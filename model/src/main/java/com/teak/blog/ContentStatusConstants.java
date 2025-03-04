package com.teak.blog;

import java.util.Map;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/3/1 00:59
 * @Project: teakWeb
 * @File: ContentStatusConstants.java
 * @Description:
 */
public class ContentStatusConstants {
    // 状态字符串常量
    public static final String DRAFT_STATUS = "草稿";
    public static final String PUBLISHED_STATUS = "已发布";
    // 状态码常量
    public static final int DRAFT_CODE = 0;
    public static final int PUBLISHED_CODE = 1;
    // 如果需要状态映射可以添加
    public static final Map<String, Integer> STATUS_TO_CODE_MAP = Map.of(
            DRAFT_STATUS, DRAFT_CODE,
            PUBLISHED_STATUS, PUBLISHED_CODE
    );
    // 如果需要反向查找可以添加
    public static final Map<Integer, String> CODE_TO_STATUS_MAP = Map.of(
            DRAFT_CODE, DRAFT_STATUS,
            PUBLISHED_CODE, PUBLISHED_STATUS
    );

    private ContentStatusConstants() {
        // 防止实例化
    }
}
