package io.github.dunwu.spring.ioc.annotation;

import io.github.dunwu.spring.ioc.annotation.instrument.Instrument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class Musician implements Performer {
    private String name;
    private String song;

    // @Autowired
    // private Instrument instrument;

    @Autowired
    private Instrument violin;

    @Autowired
    @Qualifier(value = "guitar")
    private Instrument instrument1;

    @Resource(name = "piano")
    private Instrument instrument2;


    public Musician() {}

    @Override
    public void perform() throws Exception {
        System.out.println(String.format("%s演绎%s", name, song));
        violin.play();
        // instrument.play();
        instrument1.play();
        instrument2.play();
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
