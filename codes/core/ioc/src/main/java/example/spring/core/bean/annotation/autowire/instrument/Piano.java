package example.spring.core.bean.annotation.autowire.instrument;

public class Piano implements Instrument {

    public Piano() {
    }

    @Override
    public void play() {
        System.out.println("弹奏钢琴");
    }

}
