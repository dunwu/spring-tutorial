package io.github.dunwu.springboot.core.impl;

import io.github.dunwu.springboot.core.MessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({ "dev", "default" })
public class DevMessageServiceImpl implements MessageService {

    @Value("${dunwu.message:Development Begin!}")
    private String message;

    @Override
    public String getMessage() {
        return this.message;
    }

}
