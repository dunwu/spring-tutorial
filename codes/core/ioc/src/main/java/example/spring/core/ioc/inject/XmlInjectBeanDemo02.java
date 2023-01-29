package example.spring.core.ioc.inject;

import example.spring.core.bean.entity.job.Band;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlInjectBeanDemo02 {

    public static void main(String[] args) throws Exception {

        // 创建应用上下文容器
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("/META-INF/spring-collection.xml");

        // 构造注入
        Band band = (Band) ctx.getBean("band");
        band.perform();

        // 关闭应用上下文容器，不要忘记这句话
        ctx.close();
    }

}
