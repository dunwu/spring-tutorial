package io.github.dunwu.spring.ioc.annotation.inject.instrument;
public class Violin implements Instrument {
    public Violin() {}

    @Override
    public void play() {
        System.out.println("弹奏小提琴");
    }
}
