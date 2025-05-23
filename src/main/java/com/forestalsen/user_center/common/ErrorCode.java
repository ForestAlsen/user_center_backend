package com.forestalsen.user_center.common;

/**
 * 通用状态码
 */
public enum ErrorCode {
    SUCCESS(200,"ok",""),
    PARAMS_ERROR(40000,"请求参数错误",""),
    NULL_ERROR(40001,"请求数据为空",""),
    NOT_LOGIN(40100,"未登录",""),
    NOT_AUTH(40101,"没有权限",""),
    SYSTEM_ERROR(50000,"系统异常","")


    ;


    /**
     * 状态码
     */
    private final int code;


    /**
     * 状态码信息
     */
    private final String message;

    /**
     * 状态码详情
     */
    private final String description;


    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
