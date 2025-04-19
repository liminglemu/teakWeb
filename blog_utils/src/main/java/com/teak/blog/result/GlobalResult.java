package com.teak.blog.result;

import com.teak.blog.result.enums.GlobalResultEnums;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;

/**
 * The type Global result.
 *
 * @author 柚mingle木
 * @version 1.0
 * @date 2023 /2/19
 */
@Getter
public class GlobalResult {

    private Integer code;
    private String message;
    private Object data = null;


    // 静态工厂方法（线程安全入口）
    public static GlobalResult create() {
        return new GlobalResult();
    }

    /**
     * Global result global result.
     * <p>
     * 这里使用的是饿汉式单例，我也不知道为什么怎么写，就是单纯的想写写看，这里的单例确实有效，所有消费者的hashCode都是一样的
     * <p>
     * 2025/2/26 全局返回不能写单例，因为如果使用单例，那么每次调用都会返回同一个对象，多线程返回的数据会被覆盖
     *
     * @return the global result
     */
   /* public static GlobalResult globalResult() {
        return InnClass.globalResult;
    }

    private static class InnClass {
        private static final GlobalResult globalResult = new GlobalResult();
    }*/
    public static GlobalResult success() {
        return create()
                .setCode(GlobalResultEnums.SUCCESS.getCode())
                .setMessage(GlobalResultEnums.SUCCESS.getMessage());
    }

    public static GlobalResult success(Object value) {
        return create()
                .setCode(GlobalResultEnums.SUCCESS.getCode())
                .setMessage(GlobalResultEnums.SUCCESS.getMessage())
                .setData(value);
    }

    public static GlobalResult success(Object value, String message) {
        return create()
                .setCode(GlobalResultEnums.SUCCESS.getCode())
                .setMessage(message)
                .setData(value);
    }

    public static GlobalResult error() {
        return create()
                .setCode(GlobalResultEnums.FAIL.getCode())
                .setMessage(GlobalResultEnums.FAIL.getMessage());
    }

    public static GlobalResult error(Object value) {
        return create()
                .setCode(GlobalResultEnums.FAIL.getCode())
                .setMessage(GlobalResultEnums.FAIL.getMessage())
                .setData(value);
    }

    public static GlobalResult error(Object value, String message) {
        return create()
                .setCode(GlobalResultEnums.FAIL.getCode())
                .setMessage(message)
                .setData(value);
    }

    // 重定向响应
    public static GlobalResult redirect(Object value, String message) {
        return create()
                .setCode(GlobalResultEnums.FORWARD.getCode())
                .setMessage(message)
                .setData(value);
    }

    // 优化后的链式方法
    public GlobalResult setCode(Integer code) {
        this.code = code;
        return this;
    }

    public GlobalResult setMessage(String message) {
        this.message = message;
        return this;
    }

    private GlobalResult setData(Object data) {
        this.data = data;
        return this;
    }

}
