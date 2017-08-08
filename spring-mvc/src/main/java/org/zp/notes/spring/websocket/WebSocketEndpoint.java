package org.zp.notes.spring.websocket;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Websocket 消息处理中心 使用 websocket 可以与前端建立一个双向的长连接。
 * @author Zhang Peng
 * @see https://github.com/jetty-project/embedded-jetty-websocket-examples
 */
@ServerEndpoint("/websocket")
public class WebSocketEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEndpoint.class);

    @OnMessage
    public void onMessage(String message, Session session) throws IOException, InterruptedException {

        // Print the client message for testing purposes
        if (logger.isDebugEnabled()) {
            logger.debug("Received: {}", message);
        }

        // Send the first message to the client
        session.getBasicRemote().sendText("This is the first server message");

        // Send 3 messages to the client every 5 seconds
        int sentMessages = 0;
        while (sentMessages < 5) {
            Thread.sleep(2000);
            session.getBasicRemote().sendText("This is an intermediate server message. Count: " + sentMessages);
            sentMessages++;
        }

        // Send a final message to the client
        session.getBasicRemote().sendText("This is the last server message");
    }

    @OnOpen
    public void onOpen() {
        if (logger.isDebugEnabled()) {
            logger.debug("Client connected!");
        }
    }

    @OnClose
    public void onClose() {
        if (logger.isDebugEnabled()) {
            logger.debug("Connection closed");
        }
    }
}
