package io.github.dunwu.spring.rmi.service;

public class MessageProviderImpl implements MessageProvider {
    public String getMessage(String name) {
        return "Hello, " + name;
    }
}
