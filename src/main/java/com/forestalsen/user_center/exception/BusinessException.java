package com.forestalsen.user_center.exception;

import com.forestalsen.user_center.common.ErrorCode;

/**
 * 封装全局异常
 */
public class BusinessException extends RuntimeException {

    private  final int code;
    private  final String descripton;

    public BusinessException(int code,String message,String description) {
        super(message);
        this.code = code;
        this.descripton = description;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
       this.code = errorCode.getCode();
       this.descripton = errorCode.getDescription();
    }

    public BusinessException(ErrorCode errorCode ,String description) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.descripton = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescripton() {
        return descripton;
    }
}
