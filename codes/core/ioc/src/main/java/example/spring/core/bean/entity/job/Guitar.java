package example.spring.core.bean.entity.job;

public class Guitar implements Instrument {

    public Guitar() { }

    @Override
    public String play() {
        return "演奏吉他";
    }

}
