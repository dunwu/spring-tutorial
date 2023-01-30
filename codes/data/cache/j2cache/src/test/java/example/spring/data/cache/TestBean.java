package example.spring.data.cache;

import java.io.Serializable;

public class TestBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer num;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

}
