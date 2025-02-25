package com.teak.blog.result;

import com.teak.blog.result.enums.GlobalResultEnums;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * The type Global result.
 *
 * @author 柚mingle木
 * @version 1.0
 * @date 2023 /2/19
 */
@Data
public class GlobalResult {

    private Integer code;
    private String message;
    private Map<String, Object> data;


    public GlobalResult() {
    }

    /**
     * Global result global result.
     * <p>
     * 这里使用的是饿汉式单例，我也不知道为什么怎么写，就是单纯的想写写看，这里的单例确实有效，所有消费者的hashCode都是一样的
     *
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

    /**
     * Ok global result.
     *
     * @param data the data
     * @return the global result
     */
    public @NotNull GlobalResult ok(Map<String, Object> data) {
        this.code = GlobalResultEnums.SUCCESS.getCode();
        this.message = GlobalResultEnums.SUCCESS.getMessage();
        this.data = data;
        return this;
    }

    /**
     * 构建自定义消息的全局结果对象
     *
     * @param data    需要封装的结果数据集合，以键值对形式存储的附加数据对象
     * @param message 自定义的消息内容，用于覆盖默认的失败提示信息
     * @return 当前GlobalResult实例，支持链式调用
     * <p>
     * 方法说明：
     * 1. 固定设置结果为失败状态（设置code为FAIL枚举对应的错误码）
     * 2. 使用自定义消息替换默认消息
     * 3. 绑定传入的业务数据集合
     */
    public @NotNull GlobalResult customMessage(Map<String, Object> data, String message) {
        this.code = GlobalResultEnums.FAIL.getCode();
        this.message = message;
        this.data = data;
        return this;
    }

    /**
     * Fail global result.
     *
     * @param data the data
     * @return the global result
     */
    public @NotNull GlobalResult fail(Map<String, Object> data) {
        this.code = GlobalResultEnums.FAIL.getCode();
        this.message = GlobalResultEnums.FAIL.getMessage();
        this.data = data;
        return this;
    }

    /**
     * Forward global result.
     *
     * @param data the data
     * @return the global result
     */
    public @NotNull GlobalResult forward(Map<String, Object> data) {
        this.code = GlobalResultEnums.FORWARD.getCode();
        this.message = GlobalResultEnums.FORWARD.getMessage();
        this.data = data;
        return this;
    }

}
