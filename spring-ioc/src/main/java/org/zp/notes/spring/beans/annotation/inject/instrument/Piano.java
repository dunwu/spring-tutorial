package org.zp.notes.spring.beans.annotation.inject.instrument;

public class Piano implements Instrument {
    public Piano() {}

    public void play() {
        System.out.println("弹奏钢琴");
    }
}
