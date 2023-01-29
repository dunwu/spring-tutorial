package example.spring.core.bean.entity.job;

import lombok.Data;

/**
 * 音乐家
 */
@Data
public class Musician implements Performer {

    private String name;

    private String song;

    private Instrument instrument;

    public Musician() { }

    @Override
    public void perform() {
        System.out.printf("%s%s：%s%n", name, instrument.play(), song);
    }

}
