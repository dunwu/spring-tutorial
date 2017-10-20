package io.github.dunwu.spring.rmi.service;

public class MessageProviderImpl implements MessageProvider {
    @Override
    public String getMessage(String name) {
        return "Hello, " + name;
    }
}
