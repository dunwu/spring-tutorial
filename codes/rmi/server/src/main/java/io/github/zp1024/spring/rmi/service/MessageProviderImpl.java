package io.github.zp1024.spring.rmi.service;

public class MessageProviderImpl implements MessageProvider {
    public String getMessage(String name) {
        return "Hello, " + name;
    }
}
