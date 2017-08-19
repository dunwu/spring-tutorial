package me.chongfeng.spring.ioc.annotation.instrument;

import org.springframework.stereotype.Service;

@Service("guitar")
public class Guitar implements Instrument {
    public Guitar() {}

    public void play() {
        System.out.println("弹奏吉他");
    }
}
