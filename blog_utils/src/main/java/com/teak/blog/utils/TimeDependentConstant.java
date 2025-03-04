package com.teak.blog.utils;

import java.text.SimpleDateFormat;

public class TimeDependentConstant {
    public static final String PATTERN_YEAR_MONTH = "yyyy-MM";
    public static final String PATTERN_DATE = "yyyy-MM-dd";
    public static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";
    static final String REGEX_YEAR_MONTH = "^\\d{4}-\\d{1,2}";
    static final String REGEX_DATE = "^\\d{4}-\\d{1,2}-\\d{1,2}";
    static final int MAX_WEEK_ITERATIONS = 10;
    // 使用ThreadLocal缓存SimpleDateFormat
    static final ThreadLocal<SimpleDateFormat> DATE_FORMAT_CACHE = ThreadLocal.withInitial(() -> new SimpleDateFormat(PATTERN_DATE));
    private TimeDependentConstant() {
    }

}