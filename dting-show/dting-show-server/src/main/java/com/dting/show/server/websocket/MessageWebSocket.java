package com.dting.show.server.websocket;

import org.yeauty.annotation.*;
import org.yeauty.pojo.Session;

import java.io.IOException;

/**
 * 消息交互使用的websocket
 *
 * @author huangfu
 * @date 2022年10月26日09:41:12
 */
@ServerEndpoint(path = "/ws/{sessionId}", port = "${ws.port}")
public class MessageWebSocket {

    private Session session;

    private String sessionId;


    @OnOpen
    public void onOpen(Session session, @PathVariable String sessionId) {
        this.session = session;
        this.sessionId = sessionId;
        DynamicIterativeTaskPoolService.addConnection(this);

    }

    @OnClose
    public void onClose(Session session) throws IOException {
        DynamicIterativeTaskPoolService.removeConnection(this.sessionId);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    @OnMessage
    public void onMessage(Session session, String message) {

    }

    /**
     * 发送消息
     *
     * @param message 消息对象
     */
    public void sendMessage(String message) {
        session.sendText(message);
    }


    public Session getSession() {
        return session;
    }

    public String getSessionId() {
        return sessionId;
    }
}
