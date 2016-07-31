package org.zp.notes.spring.beans.autowire.instrument;
public class Violin implements Instrument {
    public Violin() {}

    public void play() {
        System.out.println("弹奏小提琴");
    }
}