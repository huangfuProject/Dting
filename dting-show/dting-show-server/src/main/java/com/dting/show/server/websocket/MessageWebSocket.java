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
@ServerEndpoint(path = "/ws/{arg}", port = "${ws.port}")
public class MessageWebSocket {

    private Session session;

    @OnOpen
    public void onOpen(Session session, @PathVariable String arg) {
        this.session = session;
        System.out.println("new connection");
        System.out.println(arg);
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        session.close();
        System.out.println("one connection closed");

    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        System.out.println(message);
        session.sendText("Hello websocket");
    }

    /**
     * 发送消息
     *
     * @param message 消息对象
     */
    public void sendMessage(String message) {
        session.sendText(message);
    }
}
