package com.dting.show.server.websocket;

/**
 * websocket 消息体
 *
 * @author huangfu
 * @date 2022年10月30日 13点01分
 */
public class MessageEntity {

    /**
     * 内存数据
     */
    public static final String MEMORY_DATA_TYPE = "0";

    /**
     * 0：内存数据
     */
    private final String type;

    /**
     * 消息体
     */
    private final String body;

    public MessageEntity(String type, String body) {
        this.type = type;
        this.body = body;
    }

    public String getType() {
        return type;
    }

    public String getBody() {
        return body;
    }
}