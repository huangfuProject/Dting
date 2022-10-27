package com.dting.show.server.websocket;

import com.alibaba.fastjson.JSON;
import org.yeauty.annotation.*;
import org.yeauty.pojo.Session;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息交互使用的websocket
 *
 * @author huangfu
 * @date 2022年10月26日09:41:12
 */
@ServerEndpoint(path = "/ws/{sessionId}", port = "${ws.port}")
public class MessageWebSocket {

    private Session session;
    private WebsocketSendData websocketSendData;

    private final static Map<String, MessageWebSocket> SOCKET_POOL = new ConcurrentHashMap<>(32);


    @OnOpen
    public void onOpen(Session session, @PathVariable String sessionId) {
        this.session = session;

    }

    @OnClose
    public void onClose(Session session) throws IOException {
        if(WebsocketSendData.LISTENING.equals(websocketSendData.getPurpose())) {
            DynamicIterativeTaskPoolService.removeMessageWebSocket(this);
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        WebsocketSendData websocketSendData = JSON.parseObject(message, WebsocketSendData.class);
        SOCKET_POOL.put(websocketSendData.getSessionId(), this);
        this.websocketSendData = websocketSendData;
        //如果是监听类型的
        if(WebsocketSendData.LISTENING.equals(websocketSendData.getPurpose())) {
            DynamicIterativeTaskPoolService.addMessageWebSocket(this);
        }
    }

    /**
     * 发送消息
     *
     * @param message 消息对象
     */
    public void sendMessage(String message) {
        session.sendText(message);
    }

    /**
     * 获取sessionId
     *
     * @param sessionId 获取sessionId
     * @return 返回websocket的操作对象
     */
    public static MessageWebSocket getMessageWebSocket(String sessionId) {
        return SOCKET_POOL.get(sessionId);
    }

    public Session getSession() {
        return session;
    }

    public WebsocketSendData getWebsocketSendData() {
        return websocketSendData;
    }

    public void setWebsocketSendData(WebsocketSendData websocketSendData) {
        this.websocketSendData = websocketSendData;
    }
}
