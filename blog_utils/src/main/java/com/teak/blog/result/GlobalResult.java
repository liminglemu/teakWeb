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
    private Map<String, Object> data;


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

    // 成功响应（推荐使用不可变对象）
    public static GlobalResult success(Map<String, Object> data) {
        return new GlobalResult()
                .setCode(GlobalResultEnums.SUCCESS.getCode())
                .setMessage(GlobalResultEnums.SUCCESS.getMessage())
                .setData(data);
    }

    public static GlobalResult successWithMessage(Map<String, Object> data, String message) {
        return new GlobalResult()
                .setCode(GlobalResultEnums.SUCCESS.getCode())
                .setMessage(GlobalResultEnums.SUCCESS.getMessage())
                .setData(data);
    }

    // 失败响应（带默认消息）
    public static GlobalResult error(Map<String, Object> data) {
        return new GlobalResult()
                .setCode(GlobalResultEnums.FAIL.getCode())
                .setMessage(GlobalResultEnums.FAIL.getMessage())
                .setData(data != null ? data : Collections.emptyMap());
    }

    // 自定义错误响应
    public static GlobalResult errorWithMessage(Map<String, Object> data, String customMessage) {
        return new GlobalResult()
                .setCode(GlobalResultEnums.FAIL.getCode())
                .setMessage(customMessage)
                .setData(data);
    }

    // 重定向响应
    public static GlobalResult redirect(Map<String, Object> data) {
        return new GlobalResult()
                .setCode(GlobalResultEnums.FORWARD.getCode())
                .setMessage(GlobalResultEnums.FORWARD.getMessage())
                .setData(data);
    }

    // 链式方法（返回新对象保证线程安全）
    private GlobalResult setCode(Integer code) {
        GlobalResult result = new GlobalResult();
        result.code = code;
        result.message = this.message;
        result.data = this.data;
        return result;
    }

    private GlobalResult setMessage(String message) {
        GlobalResult result = new GlobalResult();
        result.code = this.code;
        result.message = message;
        result.data = this.data;
        return result;
    }

    private GlobalResult setData(Map<String, Object> data) {
        GlobalResult result = new GlobalResult();
        result.code = this.code;
        result.message = this.message;
        result.data = data != null ? Collections.unmodifiableMap(data) : null;
        return result;
    }

}
