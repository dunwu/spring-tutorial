package example.spring.core.bean.entity.job;

public class Piano implements Instrument {

    public Piano() { }

    @Override
    public String play() {
        return "演奏钢琴";
    }

}
