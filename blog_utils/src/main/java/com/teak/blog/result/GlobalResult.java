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


    private GlobalResult() {
    }

    /**
     * Global result global result.
     * <p>
     * 这里使用的是饿汉式单例，我也不知道为什么怎么写，就是单纯的想写写看，这里的单例确实有效，所有消费者的hashCode都是一样的
     *
     * @return the global result
     */
    public static GlobalResult globalResult() {
        return InnClass.globalResult;
    }

    private static class InnClass {
        private static final GlobalResult globalResult = new GlobalResult();
    }

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
