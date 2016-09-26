package org.zp.notes.spring.ioc.annotation.autowire.instrument;
public class Guitar implements Instrument {
    public Guitar() {}

    public void play() {
        System.out.println("弹奏吉他");
    }
}