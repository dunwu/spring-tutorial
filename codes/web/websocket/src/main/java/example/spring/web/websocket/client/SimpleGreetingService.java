package example.spring.web.websocket.client;

public class SimpleGreetingService implements GreetingService {

    @Override
    public String getGreeting() {
        return "Hello world!";
    }

}
