package com.teak.blog.handler;

import com.teak.blog.result.GlobalResult;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

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
    public GlobalResult handleException(@NotNull Exception e) {
        e.printStackTrace();
        HashMap<String, Object> hashMap = hashMapPut(e);
        return getResult(hashMap);
    }

    /**
     * Handle arithmetic exception global result.
     *
     * @param e the e
     * @return the global result
     */
    @ExceptionHandler(value = ArithmeticException.class)
    public GlobalResult handleArithmeticException(@NotNull ArithmeticException e) {
        e.printStackTrace();
        HashMap<String, Object> hashMap = hashMapPut(e);
        return getResult(hashMap);
    }

    /**
     * Handle runtime exception global result.
     *
     * @param e the e
     * @return the global result
     */
    @ExceptionHandler(value = RuntimeException.class)
    public GlobalResult handleRuntimeException(@NotNull RuntimeException e) {
        e.printStackTrace();
        HashMap<String, Object> hashMap = hashMapPut(e);
        return getResult(hashMap);
    }

    private @NotNull HashMap<String, Object> hashMapPut(@NotNull Exception e) {
        HashMap<String, Object> hashMap = new HashMap<>(5);
        hashMap.put("本地化消息", e.getLocalizedMessage());
        hashMap.put("原因", e.getCause());
        hashMap.put("类", e.getClass());
        hashMap.put("已抑制", e.getSuppressed());
        hashMap.put("哈希代码", e.hashCode());
        log.error(String.valueOf(hashMap));
        return hashMap;
    }

    @NotNull
    private GlobalResult getResult(HashMap<String, Object> hashMap) {
        return GlobalResult.globalResult().fail(hashMap);
    }
}
