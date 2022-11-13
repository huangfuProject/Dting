package com.dting.show.server.enums;

/**
 * *************************************************<br/>
 * 实例状态<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/11/13 9:30
 */
public enum InstanceState {

    /**
     * 上线
     */
    UP("1", "上线"),
    /**
     * 下线
     */
    DOWN("0", "下线");

    /**
     * 状态吗
     */
    private final String code;

    /**
     * 消息
     */
    private final String message;


    InstanceState(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
