package example.spring.core.bean.annotation.instrument;

import org.springframework.stereotype.Service;

@Service("guitar")
public class Guitar implements Instrument {

    public Guitar() {
    }

    @Override
    public void play() {
        System.out.println("弹奏吉他");
    }

}
