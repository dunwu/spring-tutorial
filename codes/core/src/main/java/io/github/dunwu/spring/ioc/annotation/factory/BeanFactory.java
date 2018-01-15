package io.github.dunwu.spring.ioc.annotation.factory;

public class BeanFactory {
    public void work() {
        System.out.println("工作");
    }

    private BeanFactory() {

    }

    private static class StageSingletonHolder {
        static BeanFactory instance = new BeanFactory();
    }

    public static BeanFactory getInstance() {
        return StageSingletonHolder.instance;
    }
}
