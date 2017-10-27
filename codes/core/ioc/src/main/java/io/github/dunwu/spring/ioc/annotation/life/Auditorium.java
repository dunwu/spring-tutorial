package io.github.dunwu.spring.ioc.annotation.life;

public class Auditorium {
    public void work() {
        System.out.println("工作");
    }

    public void turnOnLight() {
        System.out.println("开灯");
    }

    public void turnOffLight() {
        System.out.println("关灯");
    }
}
