package io.github.dunwu.spring.ioc.annotation.inject.instrument;
public class Guitar implements Instrument {
    public Guitar() {}

    @Override
    public void play() {
        System.out.println("弹奏吉他");
    }
}
