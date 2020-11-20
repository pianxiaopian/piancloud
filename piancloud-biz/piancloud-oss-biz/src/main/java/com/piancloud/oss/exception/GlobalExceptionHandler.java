package com.piancloud.oss.exception;


import com.piancloud.common.core.result.Result;
import com.piancloud.common.core.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author chendong
 * @title: GlobalExceptionHandler
 * @projectName ecloud-oos
 * @description: TODO 捕获全局异常
 * @date 2020/10/5 20:40
 */
@ControllerAdvice
@Order(-1)
@Slf4j
public class GlobalExceptionHandler {



/**
 * 拦截自定义异常
 */

    @ExceptionHandler(CoreException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Result coreException(CoreException e) {
        log.error("业务异常:", e);
        e.printStackTrace();
        Result result = new Result();
        result.setCode(e.getCode());
        result.setMessage(e.getMsg());
        result.setData(e);
        return result;
    }





/**
 * 拦截未知的运行时异常
 */

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Result notFount(RuntimeException e) {
        log.error("运行时异常:", e);
        e.printStackTrace();
        return Result.failure(ResultCode.SERV_UNKNOWN_EXCEPTION, null);
    }

}
