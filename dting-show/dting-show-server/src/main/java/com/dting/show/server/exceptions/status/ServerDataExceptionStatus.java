package com.dting.show.server.exceptions.status;

import com.dting.exceptions.code.IExceptionCode;

/**
 * *************************************************<br/>
 * 服务操作异常状态信息枚举<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/12/3 14:05
 */
public enum ServerDataExceptionStatus implements IExceptionCode {
    /**
     * 未知的环境信息
     */
    UNKNOWN_ENVIRONMENT_INFORMATION("server_0", "未知的环境信息！");

    /**
     * 错误码
     */
    private final String code;

    /**
     * 错误信息
     */
    private final String message;

    ServerDataExceptionStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
