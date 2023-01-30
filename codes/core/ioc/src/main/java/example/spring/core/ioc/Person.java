package example.spring.core.ioc;

import java.beans.ConstructorProperties;

public class Person {

    private final String name;
    private final int age;

    @ConstructorProperties({ "name", "age" })
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

}
