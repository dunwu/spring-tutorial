package io.github.dunwu.springboot.client;

public class SimpleGreetingService implements GreetingService {

    @Override
    public String getGreeting() {
        return "Hello world!";
    }

}
