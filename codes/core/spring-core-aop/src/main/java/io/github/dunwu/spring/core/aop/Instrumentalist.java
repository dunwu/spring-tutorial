package io.github.dunwu.spring.core.aop;

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
