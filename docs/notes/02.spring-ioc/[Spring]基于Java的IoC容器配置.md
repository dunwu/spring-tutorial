# 引言

IoC容器的配置有三种方式：

- 基于xml配置
- 基于注解配置
- 基于Java配置

作为Spring传统的配置方式，xml配置方式一般为大家所熟知。

如果厌倦了xml配置，Spring也提供了注解配置方式或Java配置方式来简化配置。

**本文，将对Java配置IoC容器做详细的介绍。**



基于Java的IoC容器配置有两个关键注解：`@Bean`和`@Configuration`

# 基本概念

基于Java配置Spring IoC容器，实际上是**Spring允许用户定义一个类，在这个类中去管理IoC容器的配置**。

为了让Spring识别这个定义类为一个Spring配置类，需要用到两个注解：`@Configuration`和`@Bean`。

如果你熟悉Spring的xml配置方式，你可以将`@Configuration`等价于`<beans>`标签；将`@Bean`等价于`<bean>`标签。



## @Bean

@Bean的修饰目标只能是方法或注解。

@Bean只能定义在`@Configuration`或`@Component`注解修饰的类中。



### 声明一个bean

此外，@Configuration类允许在同一个类中通过@Bean定义内部bean依赖。

声明一个bean，只需要在bean属性的set方法上标注@Bean即可。

```java
@Configuration
public class AnnotationConfiguration {
    private static final Logger log = LoggerFactory.getLogger(JavaComponentScan.class);

    @Bean
    public Job getPolice() {
        return new Police();
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AnnotationConfiguration.class);
        ctx.scan("org.zp.notes.spring.beans");
        ctx.refresh();
        Job job = (Job) ctx.getBean("police");
        log.debug("job: {}, work: {}", job.getClass(), job.work());
    }
}

public interface Job {
    String work();
}

@Component("police")
public class Police implements Job {
    @Override
    public String work() {
        return "抓罪犯";
    }
}
```

这等价于配置

```xml
<beans>
	<bean id="police" class="org.zp.notes.spring.ioc.sample.job.Police"/>
</beans>
```





@Bean注解用来表明一个方法实例化、配置合初始化一个被Spring IoC容器管理的新对象。

如果你熟悉Spring的xml配置，你可以将@Bean视为等价于`<beans>`标签。

@Bean注解可以用于任何的Spring `@Component` bean，然而，通常被用于`@Configuration` bean。



## @Configuration

`@Configuration`是一个类级别的注解，用来标记被修饰类的对象是一个`BeanDefinition`。

@Configuration类声明bean是通过被@Bean修饰的公共方法。此外，@Configuration类允许在同一个类中通过@Bean定义内部bean依赖。

```java
@Configuration
public class AppConfig {
    @Bean
    public MyService myService() {
        return new MyServiceImpl();
    }
}
```

这等价于配置

```xml
<beans>
	<bean id="myService" class="com.acme.services.MyServiceImpl"/>
</beans>
```

用AnnotationConfigApplicationContext实例化IoC容器



# 参考

需要参考本文范例代码的朋友，可以访问我github：

https://github.com/atlantis1024/SpringNotes/tree/develop/spring-ioc