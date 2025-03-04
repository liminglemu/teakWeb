package com.teak.blog.handler;

import com.teak.blog.result.GlobalResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.concurrent.ConcurrentHashMap;

/**
 * The type Global exception handler.
 *
 * @author 柚mingle木
 * @version 1.0
 * @date 2023 /2/19
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle exception global result.
     *
     * @param e the e
     * @return the global result
     */
    @ExceptionHandler(value = Exception.class)
    public GlobalResult handleException(Exception e) {
        ConcurrentHashMap<String, Object> concurrentHashMap = concurrentHashMapPut(e);
        return getResult(concurrentHashMap);
    }

    /**
     * Handle arithmetic exception global result.
     *
     * @param e the e
     * @return the global result
     */
    @ExceptionHandler(value = ArithmeticException.class)
    public GlobalResult handleArithmeticException(ArithmeticException e) {
        ConcurrentHashMap<String, Object> concurrentHashMap = concurrentHashMapPut(e);
        return getResult(concurrentHashMap);
    }

    /**
     * Handle runtime exception global result.
     *
     * @param e the e
     * @return the global result
     */
    @ExceptionHandler(value = RuntimeException.class)
    public GlobalResult handleRuntimeException(RuntimeException e) {
        ConcurrentHashMap<String, Object> concurrentHashMap = concurrentHashMapPut(e);
        return getResult(concurrentHashMap);
    }

    private ConcurrentHashMap<String, Object> concurrentHashMapPut(Exception e) {
        e.printStackTrace();
        ConcurrentHashMap<String, Object> concurrentHashMap = new ConcurrentHashMap<>(5);
        concurrentHashMap.put("本地化消息", e.getLocalizedMessage());
        concurrentHashMap.put("原因", e.getCause());
        concurrentHashMap.put("类", e.getClass());
        concurrentHashMap.put("已抑制", e.getSuppressed());
        concurrentHashMap.put("哈希代码", e.hashCode());
        log.error(String.valueOf(concurrentHashMap));
        return concurrentHashMap;
    }


    private GlobalResult getResult(ConcurrentHashMap<String, Object> concurrentHashMap) {
        return GlobalResult.error(concurrentHashMap);
    }
}
