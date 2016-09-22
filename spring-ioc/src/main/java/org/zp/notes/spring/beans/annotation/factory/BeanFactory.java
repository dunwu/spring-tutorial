package org.zp.notes.spring.beans.annotation.factory;

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
