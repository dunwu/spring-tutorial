package example.spring.core.bean.action;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class TestC {

    @Getter
    private final TestD testD;

    @Autowired
    public TestC(@Lazy TestD testD) {
        this.testD = testD;
    }

}
