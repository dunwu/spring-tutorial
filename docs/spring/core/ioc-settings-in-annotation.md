# 概述

Spring2.5 引入了注解。 
于是，一个问题产生了：**使用注解方式注入 JavaBean 是不是一定完爆 xml方式？** 
未必。正所谓，仁者见仁智者见智。任何事物都有其优缺点，看你如何取舍。来看看注解的优缺点： 
**`优点`**：大大减少了配置，并且可以使配置更加精细——类，方法，字段都可以用注解去标记。 
**`缺点`**：使用注解，不可避免产生了侵入式编程，也产生了一些问题。
- 你需要将注解加入你的源码并编译它；  

- 注解往往比较分散，不易管控。

> 注：spring 中，先进行注解注入，然后才是xml注入，因此如果注入的目标相同，后者会覆盖前者。



# 启动注解 #
Spring 默认是不启用注解的。如果想使用注解，需要先在xml中启动注解。
启动方式：在xml中加入一个标签，很简单吧。  
```xml
<context:annotation-config/>
```
> 注：`<context:annotation-config/>` 只会检索定义它的上下文。什么意思呢？就是说，如果你
> 为DispatcherServlet指定了一个`WebApplicationContext`，那么它只在controller中查找`@Autowired`注解，而不会检查其它的路径。



# Spring注解 #

## @Required

`@Required` 注解只能用于修饰bean属性的setter方法。受影响的bean属性必须在配置时被填充在xml配置文件中，否则容器将抛出`BeanInitializationException`。

```java
public class AnnotationRequired {
    private String name;
    private String sex;

    public String getName() {
        return name;
    }

    /**
     * @Required 注解用于bean属性的setter方法并且它指示，受影响的bean属性必须在配置时被填充在xml配置文件中，
     *           否则容器将抛出BeanInitializationException。
     */
    @Required
    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
```



## @Autowired

`@Autowired`注解可用于修饰属性、setter方法、构造方法。

> 注：`@Autowired`注解也可用于修饰构造方法，但如果类中只有默认构造方法，则没有必要。如果有多个构造器，至少应该修饰一个，来告诉容器哪一个必须使用。

可以使用JSR330的注解`@Inject`来替代`@Autowired`。

***范例***

```java
public class AnnotationAutowired {
    private static final Logger log = LoggerFactory.getLogger(AnnotationRequired.class);

    @Autowired
    private Apple fieldA;

    private Banana fieldB;

    private Orange fieldC;

    public Apple getFieldA() {
        return fieldA;
    }

    public void setFieldA(Apple fieldA) {
        this.fieldA = fieldA;
    }

    public Banana getFieldB() {
        return fieldB;
    }

    @Autowired
    public void setFieldB(Banana fieldB) {
        this.fieldB = fieldB;
    }

    public Orange getFieldC() {
        return fieldC;
    }

    public void setFieldC(Orange fieldC) {
        this.fieldC = fieldC;
    }

    public AnnotationAutowired() {}

    @Autowired
    public AnnotationAutowired(Orange fieldC) {
        this.fieldC = fieldC;
    }

    public static void main(String[] args) throws Exception {
        AbstractApplicationContext ctx =
                        new ClassPathXmlApplicationContext("spring/spring-annotation.xml");

        AnnotationAutowired annotationAutowired =
                        (AnnotationAutowired) ctx.getBean("annotationAutowired");
        log.debug("fieldA: {}, fieldB:{}, fieldC:{}", annotationAutowired.getFieldA().getName(),
                        annotationAutowired.getFieldB().getName(),
                        annotationAutowired.getFieldC().getName());
        ctx.close();
    }
}
```

xml中的配置

```xml
<!-- 测试@Autowired -->
<bean id="apple" class="org.zp.notes.spring.beans.annotation.sample.Apple"/>
<bean id="potato" class="org.zp.notes.spring.beans.annotation.sample.Banana"/>
<bean id="tomato" class="org.zp.notes.spring.beans.annotation.sample.Orange"/>
<bean id="annotationAutowired" class="org.zp.notes.spring.beans.annotation.sample.AnnotationAutowired"/>
```



## @Qualifier

在`@Autowired`注解中，提到了如果发现有多个候选的bean都符合修饰类型，Spring就会抓瞎了。

那么，如何解决这个问题。

可以通过`@Qualifier`指定bean名称来锁定真正需要的那个bean。

***范例***

```java
public class AnnotationQualifier {
    private static final Logger log = LoggerFactory.getLogger(AnnotationQualifier.class);

    @Autowired
    @Qualifier("dog") /** 去除这行，会报异常 */
    Animal dog;

    Animal cat;

    public Animal getDog() {
        return dog;
    }

    public void setDog(Animal dog) {
        this.dog = dog;
    }

    public Animal getCat() {
        return cat;
    }

    @Autowired
    public void setCat(@Qualifier("cat") Animal cat) {
        this.cat = cat;
    }

    public static void main(String[] args) throws Exception {
        AbstractApplicationContext ctx =
                new ClassPathXmlApplicationContext("spring/spring-annotation.xml");

        AnnotationQualifier annotationQualifier =
                (AnnotationQualifier) ctx.getBean("annotationQualifier");

        log.debug("Dog name: {}", annotationQualifier.getDog().getName());
        log.debug("Cat name: {}", annotationQualifier.getCat().getName());
        ctx.close();
    }
}

abstract class Animal {
    public String getName() {
        return null;
    }
}

class Dog extends Animal {
    public String getName() {
        return "狗";
    }
}

class Cat extends Animal {
    public String getName() {
        return "猫";
    }
}
```

xml中的配置

```xml
<!-- 测试@Qualifier -->
<bean id="dog" class="org.zp.notes.spring.beans.annotation.sample.Dog"/>
<bean id="cat" class="org.zp.notes.spring.beans.annotation.sample.Cat"/>
<bean id="annotationQualifier" class="org.zp.notes.spring.beans.annotation.sample.AnnotationQualifier"/>
```



# JSR 250注解

## @Resource

Spring支持 JSP250规定的注解`@Resource`。这个注解根据指定的名称来注入bean。

如果没有为`@Resource`指定名称，它会像`@Autowired`一样按照类型去寻找匹配。

在Spring中，由`CommonAnnotationBeanPostProcessor`来处理`@Resource`注解。

***范例***

```java
public class AnnotationResource {
    private static final Logger log = LoggerFactory.getLogger(AnnotationResource.class);

    @Resource(name = "flower")
    Plant flower;

    @Resource(name = "tree")
    Plant tree;

    public Plant getFlower() {
        return flower;
    }

    public void setFlower(Plant flower) {
        this.flower = flower;
    }

    public Plant getTree() {
        return tree;
    }

    public void setTree(Plant tree) {
        this.tree = tree;
    }

    public static void main(String[] args) throws Exception {
        AbstractApplicationContext ctx =
                        new ClassPathXmlApplicationContext("spring/spring-annotation.xml");

        AnnotationResource annotationResource =
                        (AnnotationResource) ctx.getBean("annotationResource");
        log.debug("type: {}, name: {}", annotationResource.getFlower().getClass(), annotationResource.getFlower().getName());
        log.debug("type: {}, name: {}", annotationResource.getTree().getClass(), annotationResource.getTree().getName());
        ctx.close();
    }
}
```

xml的配置

```xml
<!-- 测试@Resource -->
<bean id="flower" class="org.zp.notes.spring.beans.annotation.sample.Flower"/>
<bean id="tree" class="org.zp.notes.spring.beans.annotation.sample.Tree"/>
<bean id="annotationResource" class="org.zp.notes.spring.beans.annotation.sample.AnnotationResource"/>
```



## @PostConstruct和@PreDestroy

`@PostConstruct`和`@PreDestroy`是JSR 250规定的用于生命周期的注解。

从其名号就可以看出，一个是在构造之后调用的方法，一个是销毁之前调用的方法。

```java
public class AnnotationPostConstructAndPreDestroy {
    private static final Logger log = LoggerFactory.getLogger(AnnotationPostConstructAndPreDestroy.class);

    @PostConstruct
    public void init() {
        log.debug("call @PostConstruct method");
    }

    @PreDestroy
    public void destroy() {
        log.debug("call @PreDestroy method");
    }
}
```



# JSR 330注解

从Spring3.0开始，Spring支持JSR 330标准注解（依赖注入）。

注：如果要使用JSR 330注解，需要使用外部jar包。

若你使用maven管理jar包，只需要添加依赖到pom.xml即可：

```xml
<dependency>
  <groupId>javax.inject</groupId>
  <artifactId>javax.inject</artifactId>
  <version>1</version>
</dependency>
```



## @Inject

`@Inject`和`@Autowired`一样，可以修饰属性、setter方法、构造方法。

***范例***

```java
public class AnnotationInject {
    private static final Logger log = LoggerFactory.getLogger(AnnotationInject.class);
    @Inject
    Apple fieldA;

    Banana fieldB;

    Orange fieldC;

    public Apple getFieldA() {
        return fieldA;
    }

    public void setFieldA(Apple fieldA) {
        this.fieldA = fieldA;
    }

    public Banana getFieldB() {
        return fieldB;
    }

    @Inject
    public void setFieldB(Banana fieldB) {
        this.fieldB = fieldB;
    }

    public Orange getFieldC() {
        return fieldC;
    }

    public AnnotationInject() {}

    @Inject
    public AnnotationInject(Orange fieldC) {
        this.fieldC = fieldC;
    }

    public static void main(String[] args) throws Exception {
        AbstractApplicationContext ctx =
                        new ClassPathXmlApplicationContext("spring/spring-annotation.xml");
        AnnotationInject annotationInject = (AnnotationInject) ctx.getBean("annotationInject");

        log.debug("type: {}, name: {}", annotationInject.getFieldA().getClass(),
                        annotationInject.getFieldA().getName());

        log.debug("type: {}, name: {}", annotationInject.getFieldB().getClass(),
                        annotationInject.getFieldB().getName());

        log.debug("type: {}, name: {}", annotationInject.getFieldC().getClass(),
                        annotationInject.getFieldC().getName());

        ctx.close();
    }
}
```