package org.zp.notes.spring.beans.inject.instrument;
public class Guitar implements Instrument {
    public Guitar() {}

    public void play() {
        System.out.println("弹奏吉他");
    }
}