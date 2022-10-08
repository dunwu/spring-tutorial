package io.github.dunwu.springboot.web.client;

public class SimpleGreetingService implements GreetingService {

    @Override
    public String getGreeting() {
        return "Hello world!";
    }

}
