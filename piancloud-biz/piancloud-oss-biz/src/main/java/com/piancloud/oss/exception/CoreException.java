package com.piancloud.oss.exception;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chendong
 * @title: CoreException
 * @projectName ecloud-oos
 * @description: TODO
 * @date 2020/10/1413:13
 */
@Slf4j
@Data
public class CoreException extends Exception{

    private static final long serialVersionUID = 1L;

    private Integer code;
    private String msg;

    public CoreException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
