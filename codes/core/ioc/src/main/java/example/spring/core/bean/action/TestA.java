package example.spring.core.bean.action;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestA {

    @Autowired
    @Getter
    private TestB testB;

}
