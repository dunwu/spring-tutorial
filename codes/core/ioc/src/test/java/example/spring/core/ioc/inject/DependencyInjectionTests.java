package example.spring.core.ioc.inject;

import cn.hutool.json.JSONUtil;
import example.spring.core.bean.entity.job.Musician;
import example.spring.core.bean.entity.job.Poet;
import example.spring.core.bean.entity.person.City;
import example.spring.core.bean.entity.person.User;
import example.spring.core.ioc.Person;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@DisplayName("Spring 依赖注入测试集")
public class DependencyInjectionTests {

    @Test
    @DisplayName("依赖注入基本方式对比")
    public void testBasicDependencyInjection() {
        // 配置 XML 配置文件
        // 启动 Spring 应用上下文
        ApplicationContext applicationContext =
            new ClassPathXmlApplicationContext("classpath:/META-INF/ioc/DependencyInject.xml");

        // 测试构造注入
        UserHolder userByConstructor = applicationContext.getBean("userByConstructor", UserHolder.class);
        System.out.println(JSONUtil.toJsonStr(userByConstructor.getUser()));
        Assertions.assertThat(userByConstructor.getUser().getId()).isEqualTo(1);
        Assertions.assertThat(userByConstructor.getUser().getName()).isEqualTo("钝悟");
        Assertions.assertThat(userByConstructor.getUser().getCity()).isEqualTo(City.SHANGHAI);

        // 测试 Setter 注入
        UserHolder userBySetter = applicationContext.getBean("userBySetter", UserHolder.class);
        System.out.println(JSONUtil.toJsonStr(userBySetter.getUser()));
        Assertions.assertThat(userBySetter.getUser().getId()).isEqualTo(1);
        Assertions.assertThat(userBySetter.getUser().getName()).isEqualTo("钝悟");
        Assertions.assertThat(userBySetter.getUser().getCity()).isEqualTo(City.SHANGHAI);

        // 测试构造注入自动装配
        UserHolder userByConstructorAutowiring =
            applicationContext.getBean("userByConstructorAutowiring", UserHolder.class);
        System.out.println(JSONUtil.toJsonStr(userByConstructorAutowiring.getUser()));
        Assertions.assertThat(userByConstructorAutowiring.getUser().getId()).isEqualTo(1);
        Assertions.assertThat(userByConstructorAutowiring.getUser().getName()).isEqualTo("钝悟");
        Assertions.assertThat(userByConstructorAutowiring.getUser().getCity()).isEqualTo(City.SHANGHAI);

        // 测试 Setter 注入自动装配
        UserHolder userBySetterAutowiring = applicationContext.getBean("userBySetterAutowiring", UserHolder.class);
        System.out.println(JSONUtil.toJsonStr(userBySetterAutowiring.getUser()));
        Assertions.assertThat(userBySetterAutowiring.getUser().getId()).isEqualTo(1);
        Assertions.assertThat(userBySetterAutowiring.getUser().getName()).isEqualTo("钝悟");
        Assertions.assertThat(userBySetterAutowiring.getUser().getCity()).isEqualTo(City.SHANGHAI);
    }

    @Nested
    @DisplayName("构造器依赖注入")
    class ConstructorDependencyInjectionTests {

        @Test
        @DisplayName("xml 方式构造器依赖注入测试例一")
        public void testXmlConstructorDI() {
            DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
            XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);

            // 加载 XML 资源，解析并且生成 BeanDefinition
            beanDefinitionReader.loadBeanDefinitions("classpath:/META-INF/ioc/DependencyInject.xml");
            // 依赖查找并且创建 Bean
            UserHolder userHolder = beanFactory.getBean("userByConstructor", UserHolder.class);
            Assertions.assertThat(userHolder).isNotNull();
            System.out.println(userHolder);
        }

        @Test
        @DisplayName("xml 方式构造器依赖注入测试例二")
        public void testXmlConstructorDI2() {
            // 配置 XML 配置文件
            // 启动 Spring 应用上下文
            ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:/META-INF/ioc/DependencyInject.xml");
            Poet poet = applicationContext.getBean(Poet.class);
            Assertions.assertThat(JSONUtil.toJsonStr(poet))
                      .isEqualTo("{\"name\":\"李白\",\"poetry\":{\"name\":\"将进酒\"}}");
        }

        @Test
        @DisplayName("注解方式构造器依赖注入")
        public void testAnnotationConstructorDI() {
            // 创建 BeanFactory 容器
            AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
            // 注册 Configuration Class（配置类）
            applicationContext.register(DependencyInjectionTests.class);

            // 启动 Spring 应用上下文
            applicationContext.refresh();

            // 依赖查找并且创建 Bean
            UserHolder userHolder = applicationContext.getBean(UserHolder.class);
            System.out.println(userHolder);

            // 显示地关闭 Spring 应用上下文
            applicationContext.close();
        }

        @Test
        @DisplayName("Java API 方式构造器依赖注入")
        public void testApiConstructorDI() {

            // 创建 BeanFactory 容器
            AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

            // 生成 UserHolder 的 BeanDefinition
            BeanDefinitionBuilder definitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(UserHolder.class);
            definitionBuilder.addConstructorArgReference("superUser");
            BeanDefinition userHolderBeanDefinition = definitionBuilder.getBeanDefinition();

            // 注册 UserHolder 的 BeanDefinition
            applicationContext.registerBeanDefinition("userHolder", userHolderBeanDefinition);

            XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);

            String xmlResourcePath = "classpath:/META-INF/ioc/DependencyInject.xml";
            // 加载 XML 资源，解析并且生成 BeanDefinition
            beanDefinitionReader.loadBeanDefinitions(xmlResourcePath);

            // 启动 Spring 应用上下文
            applicationContext.refresh();

            // 依赖查找并且创建 Bean
            UserHolder userHolder = applicationContext.getBean("userHolder", UserHolder.class);
            System.out.println(userHolder);

            // 显示地关闭 Spring 应用上下文
            applicationContext.close();
        }

        @Test
        @DisplayName("构造参数匹配测试1")
        public void testConstructor01() {
            // 配置 XML 配置文件
            // 启动 Spring 应用上下文
            ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:/META-INF/ioc/DependencyInject.xml");

            Person personA = applicationContext.getBean("personA", Person.class);
            Assertions.assertThat(JSONUtil.toJsonStr(personA)).isEqualTo("{\"name\":\"张三\",\"age\":18}");

            Person personB = applicationContext.getBean("personB", Person.class);
            Assertions.assertThat(JSONUtil.toJsonStr(personB)).isEqualTo("{\"name\":\"李四\",\"age\":19}");
        }

        @Test
        @DisplayName("构造参数匹配测试2")
        public void testConstructor02() {
            // 配置 XML 配置文件
            // 启动 Spring 应用上下文
            ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:/META-INF/ioc/DependencyInject.xml");

            Person personC = applicationContext.getBean("personC", Person.class);
            Assertions.assertThat(JSONUtil.toJsonStr(personC)).isEqualTo("{\"name\":\"王五\",\"age\":25}");
        }

    }

    @Nested
    @DisplayName("Setter 依赖注入")
    class SetterDependencyInjectionTests {

        @Test
        @DisplayName("xml 方式 Setter 依赖注入测试例一")
        public void testXmlSetterDI() {
            DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
            XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);

            // 加载 XML 资源，解析并且生成 BeanDefinition
            beanDefinitionReader.loadBeanDefinitions("classpath:/META-INF/ioc/DependencyInject.xml");
            // 依赖查找并且创建 Bean
            UserHolder userHolder = beanFactory.getBean("userBySetter", UserHolder.class);
            System.out.println(userHolder);
        }

        @Test
        @DisplayName("xml 方式 Setter 依赖注入测试例二")
        public void testXmlSetterDI2() {
            // 配置 XML 配置文件
            // 启动 Spring 应用上下文
            ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:/META-INF/ioc/DependencyInject.xml");
            Musician musician = applicationContext.getBean(Musician.class);
            Assertions.assertThat(musician.getName()).isEqualTo("肖邦");
            Assertions.assertThat(musician.getSong()).isEqualTo("夜曲");
            Assertions.assertThat(musician.getInstrument().play()).isEqualTo("演奏钢琴");
        }

        @Test
        @DisplayName("注解方式 Setter 依赖注入测试")
        public void testAnnotationSetterDI() {

            // 创建 BeanFactory 容器
            AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
            // 注册 Configuration Class（配置类）
            applicationContext.register(AnnotationDependencySetterInjectionDemo.class);

            XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);

            String xmlResourcePath = "classpath:/META-INF/ioc/DependencyInject.xml";
            // 加载 XML 资源，解析并且生成 BeanDefinition
            beanDefinitionReader.loadBeanDefinitions(xmlResourcePath);

            // 启动 Spring 应用上下文
            applicationContext.refresh();

            // 依赖查找并且创建 Bean
            UserHolder userHolder = applicationContext.getBean(UserHolder.class);
            System.out.println(userHolder);

            // 显示地关闭 Spring 应用上下文
            applicationContext.close();
        }

        @Test
        @DisplayName("Java API 方式 Setter 依赖注入")
        public void testApiSetterDI() {

            // 创建 BeanFactory 容器
            AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

            // 生成 UserHolder 的 BeanDefinition
            BeanDefinitionBuilder definitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(UserHolder.class);
            definitionBuilder.addPropertyReference("user", "superUser");
            BeanDefinition userHolderBeanDefinition = definitionBuilder.getBeanDefinition();

            // 注册 UserHolder 的 BeanDefinition
            applicationContext.registerBeanDefinition("userHolder", userHolderBeanDefinition);

            XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);

            String xmlResourcePath = "classpath:/META-INF/ioc/DependencyInject.xml";
            // 加载 XML 资源，解析并且生成 BeanDefinition
            beanDefinitionReader.loadBeanDefinitions(xmlResourcePath);

            // 启动 Spring 应用上下文
            applicationContext.refresh();

            // 依赖查找并且创建 Bean
            UserHolder userHolder = applicationContext.getBean("userHolder", UserHolder.class);
            System.out.println(userHolder);

            // 显示地关闭 Spring 应用上下文
            applicationContext.close();
        }

    }

    @Nested
    @DisplayName("字段依赖注入")
    class FieldDependencyInjectionTests {

        @Test
        @DisplayName("自动装配字段依赖注入")
        public void testAutoWiringFieldDI() {
            // 创建 BeanFactory 容器
            AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
            // 注册 Configuration Class（配置类） -> Spring Bean
            applicationContext.register(AnnotationDependencyFieldInjectionDemo.class);

            XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);

            String xmlResourcePath = "classpath:/META-INF/ioc/DependencyInject.xml";
            // 加载 XML 资源，解析并且生成 BeanDefinition
            beanDefinitionReader.loadBeanDefinitions(xmlResourcePath);

            // 启动 Spring 应用上下文
            applicationContext.refresh();

            // 依赖查找 AnnotationDependencyFieldInjectionDemo Bean
            AnnotationDependencyFieldInjectionDemo demo =
                applicationContext.getBean(AnnotationDependencyFieldInjectionDemo.class);

            // @Autowired 字段关联
            System.out.println(demo.userHolder);
            System.out.println(demo.userHolder2);
            Assertions.assertThat(demo.userHolder).isEqualTo(demo.userHolder2);

            // 显示地关闭 Spring 应用上下文
            applicationContext.close();
        }

    }

    @Nested
    @DisplayName("方法依赖注入")
    class MethodDependencyInjectionTests {

        @Test
        @DisplayName("自动装配方法依赖注入")
        public void testAutoWiringFieldDI() {

            // 创建 BeanFactory 容器
            AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
            // 注册 Configuration Class（配置类） -> Spring Bean
            applicationContext.register(AnnotationDependencyMethodInjectionDemo.class);

            XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);

            String xmlResourcePath = "classpath:/META-INF/ioc/DependencyInject.xml";
            // 加载 XML 资源，解析并且生成 BeanDefinition
            beanDefinitionReader.loadBeanDefinitions(xmlResourcePath);

            // 启动 Spring 应用上下文
            applicationContext.refresh();

            // 依赖查找 AnnotationDependencyFieldInjectionDemo Bean
            AnnotationDependencyMethodInjectionDemo demo =
                applicationContext.getBean(AnnotationDependencyMethodInjectionDemo.class);

            // @Autowired 字段关联
            System.out.println(demo.userHolder);
            System.out.println(demo.userHolder2);
            // 此处相等，说明 @Autowired 和 @Resource 注入，并不会重复创建 Bean
            Assertions.assertThat(demo.userHolder).isEqualTo(demo.userHolder2);

            // 显示地关闭 Spring 应用上下文
            applicationContext.close();
        }

    }

    @Nested
    @DisplayName("接口回调依赖注入")
    class AwareDependencyInjectionTests {

        @Test
        @DisplayName("接口回调依赖注入")
        public void testAutoWiringAwareDI() {

            // 创建 BeanFactory 容器
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
            // 注册 Configuration Class（配置类） -> Spring Bean
            context.register(AwareInterfaceDependencyInjectionDemo.class);

            // 启动 Spring 应用上下文
            context.refresh();

            System.out.println(AwareInterfaceDependencyInjectionDemo.applicationContext == context);
            System.out.println(AwareInterfaceDependencyInjectionDemo.beanFactory == context.getBeanFactory());
            Assertions.assertThat(context).isEqualTo(AwareInterfaceDependencyInjectionDemo.applicationContext);
            Assertions.assertThat(context.getBeanFactory())
                      .isEqualTo(AwareInterfaceDependencyInjectionDemo.beanFactory);

            // 显示地关闭 Spring 应用上下文
            context.close();
        }

    }

    @Nested
    @DisplayName("自动装配方式依赖注入")
    class AutoWiringDependencyInjectionTests {

        @Test
        @DisplayName("自动装配方式构造器依赖注入")
        public void testAutoWiringConstructorDI() {
            DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

            // 加载 XML 资源，解析并且生成 BeanDefinition
            XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
            beanDefinitionReader.loadBeanDefinitions("classpath:/META-INF/ioc/DependencyInject.xml");

            // 依赖查找并且创建 Bean
            UserHolder userHolder = beanFactory.getBean("userByConstructorAutowiring", UserHolder.class);
            System.out.println(userHolder);
        }

    }

    @Nested
    @DisplayName("延迟依赖注入")
    class LazyDependencyInjectionTests {

        @Test
        public void test() {

            // 创建 BeanFactory 容器
            AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
            // 注册 Configuration Class（配置类） -> Spring Bean
            applicationContext.register(LazyAnnotationDependencyInjectionDemo.class);

            // 加载 XML 资源，解析并且生成 BeanDefinition
            XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
            beanDefinitionReader.loadBeanDefinitions("classpath:/META-INF/ioc/DependencyInject.xml");

            // 启动 Spring 应用上下文
            applicationContext.refresh();

            // 依赖查找 QualifierAnnotationDependencyInjectionDemo Bean
            LazyAnnotationDependencyInjectionDemo demo =
                applicationContext.getBean(LazyAnnotationDependencyInjectionDemo.class);

            // 期待输出 superUser Bean
            System.out.println("demo.user = " + demo.user);
            // 期待输出 superUser Bean
            System.out.println("demo.userObjectProvider = " + demo.userObjectProvider.getObject()); // 继承 ObjectFactory
            // 期待输出 superUser user Beans
            System.out.println("demo.usersObjectFactory = " + demo.usersObjectFactory.getObject());

            Assertions.assertThat(demo.userObjectProvider).isNotEmpty();
            demo.userObjectProvider.forEach(System.out::println);

            // 显示地关闭 Spring 应用上下文
            applicationContext.close();
        }

    }

    @Bean
    public User user() {
        return User.createUser();
    }

    @Bean
    public UserHolder userHolder(User user) {
        return new UserHolder(user);
    }

}
