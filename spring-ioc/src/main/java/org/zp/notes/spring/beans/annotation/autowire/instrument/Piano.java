package org.zp.notes.spring.beans.annotation.autowire.instrument;

public class Piano implements Instrument {
    public Piano() {}

    public void play() {
        System.out.println("弹奏钢琴");
    }
}
