package example.spring.core.binding;

public class TestBean {

    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "TestBean{" + "num=" + num + '}';
    }

}
