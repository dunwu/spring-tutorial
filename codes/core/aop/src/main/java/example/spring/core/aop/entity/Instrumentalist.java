package example.spring.core.aop.entity;

import org.springframework.stereotype.Component;

@Component
public class Instrumentalist implements Performer {

    @Override
    public String perform() {
        String action = "play a song";
        System.out.println(action);
        return action;
    }

}
