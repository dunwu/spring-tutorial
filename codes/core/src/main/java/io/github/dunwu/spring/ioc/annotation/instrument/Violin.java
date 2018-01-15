package io.github.dunwu.spring.ioc.annotation.instrument;

import org.springframework.stereotype.Service;

@Service
public class Violin implements Instrument {
    public Violin() {}

    @Override
    public void play() {
        System.out.println("弹奏小提琴");
    }
}
