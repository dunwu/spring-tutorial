package example.spring.core.bean.entity.job;

public class Violin implements Instrument {

    public Violin() { }

    @Override
    public String play() {
        return "演奏小提琴";
    }

}
