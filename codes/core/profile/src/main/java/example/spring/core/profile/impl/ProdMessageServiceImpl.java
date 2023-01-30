package example.spring.core.profile.impl;

import example.spring.core.profile.MessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({ "prod" })
public class ProdMessageServiceImpl implements MessageService {

    @Value("${dunwu.message:Production Begin!}")
    private String message;

    @Override
    public String getMessage() {
        return this.message;
    }

}
