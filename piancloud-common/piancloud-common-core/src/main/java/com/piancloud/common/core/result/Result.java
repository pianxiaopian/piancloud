package com.piancloud.common.core.result;

import java.io.Serializable;

/**
 *  统一返回结果对象
 * @param <T>
 */
public class Result<T> implements Serializable {
    /**
     * 返回代码
     * @required
     */
    private Integer code;
    /**
     * 返回信息
     * @required
     */
    private String message;
    /**
     * 数据
     */
    private T data;

    public static <T> Result<T> success(){
        return new Result<>(com.piancloud.common.core.result.ResultCode.SUCCESS);
    }

    public static <T> Result<T> success(T data){
        return  new Result<>(data);
    }

    public static <T> Result<T> failure(com.piancloud.common.core.result.ResultCode resultCode, T data){
        return new Result<>(resultCode,data);
    }



    public Result(){
        super();
    }

    public Result(com.piancloud.common.core.result.ResultCode resultCode){
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    public Result(com.piancloud.common.core.result.ResultCode resultCode, T data){
        this.code = resultCode.code();
        this.message = resultCode.message();
        this.data = data;
    }

    public Result(T data){
        this.code = com.piancloud.common.core.result.ResultCode.SUCCESS.code();
        this.message = com.piancloud.common.core.result.ResultCode.SUCCESS.message();
        this.data = data;
    }

    public Result(Integer code, String message, T data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
