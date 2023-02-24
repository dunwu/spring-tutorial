package example.spring.web.websocket.reverse;

import java.io.IOException;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/reverse")
public class ReverseWebSocketEndpoint {

    @OnMessage
    public void handleMessage(Session session, String message) throws IOException {
        session.getBasicRemote().sendText("Reversed: " + new StringBuilder(message).reverse());
    }

}
