package io.github.dunwu.spring.ioc.annotation.inject.instrument;

public class Piano implements Instrument {
    public Piano() {}

    @Override
    public void play() {
        System.out.println("弹奏钢琴");
    }
}
