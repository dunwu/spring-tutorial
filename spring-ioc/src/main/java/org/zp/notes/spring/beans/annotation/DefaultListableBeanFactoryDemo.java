package org.zp.notes.spring.beans.annotation;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * 
 * @author zhangpeng0913
 * @since 2016年9月19日
 */
public class DefaultListableBeanFactoryDemo {
    public static void main(String[] args) {
        ClassPathResource res = new ClassPathResource("/spring/spring-beans.xml");
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(res);

        ApplicationContext context =
                new ClassPathXmlApplicationContext(new String[] {"services.xml", "daos.xml"});
    }
}
