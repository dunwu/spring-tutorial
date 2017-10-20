package io.github.dunwu.spring.ioc.annotation.instrument;

import org.springframework.stereotype.Service;

@Service("piano")
public class Piano implements Instrument {
    public Piano() {}

    @Override
    public void play() {
        System.out.println("弹奏钢琴");
    }
}
