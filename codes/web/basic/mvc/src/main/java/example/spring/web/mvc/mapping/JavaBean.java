package example.spring.web.mvc.mapping;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JavaBean {

    private String foo = "bar";

    private String fruit = "apple";

    @Override
    public String toString() {
        return "JavaBean {foo=[" + foo + "], fruit=[" + fruit + "]}";
    }

    public String getFoo() {
        return foo;
    }

    public void setFoo(String foo) {
        this.foo = foo;
    }

    public String getFruit() {
        return fruit;
    }

    public void setFruit(String fruit) {
        this.fruit = fruit;
    }

}
