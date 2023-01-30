package example.spring.core.bean.action;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestD {

    @Getter
    private final TestC testC;

    @Autowired
    public TestD(TestC testC) {
        this.testC = testC;
    }

}
