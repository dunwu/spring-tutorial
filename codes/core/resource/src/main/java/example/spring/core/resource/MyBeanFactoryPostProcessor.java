package example.spring.core.resource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    public MyBeanFactoryPostProcessor() {
        super();
        System.out.println("[BeanFactoryPostProcessor] construct");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("[BeanFactoryPostProcessor] BeanFactoryPostProcessor call postProcessBeanFactory");
        String[] beanNamesForType = beanFactory.getBeanNamesForType(Person.class);
        for (String name : beanNamesForType) {
            BeanDefinition bd = beanFactory.getBeanDefinition(name);
            bd.getPropertyValues().addPropertyValue("phone", "110");
        }
    }

}
