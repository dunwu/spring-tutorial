package example.spring.core.bean.annotation.factory;

public class BeanFactory {

    private BeanFactory() { }

    public static BeanFactory getInstance() {
        return StageSingletonHolder.instance;
    }

    public void work() {
        System.out.println("工作");
    }

    private static class StageSingletonHolder {

        static BeanFactory instance = new BeanFactory();

    }

}
