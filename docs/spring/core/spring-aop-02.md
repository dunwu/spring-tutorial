---
title: Spring AOP（二）
date: 2017/11/08
categories:
- spring
tags:
- spring
- core
- aop
---

## @AspectJ 支持

**@AspectJ** 是一种使用 Java 注解来实现 AOP 的编码风格。
@AspectJ 风格的 AOP 是 AspectJ Project 在 AspectJ 5 中引入的, 并且 Spring 也支持 @AspectJ 的 AOP 风格.

### 使能 @AspectJ 支持

@AspectJ 可以以 XML 的方式或以注解的方式来使能, 并且不论以哪种方式使能@ASpectJ, 我们都必须保证 aspectjweaver.jar 在 classpath 中.

#### 使用 Java Configuration 方式使能@AspectJ

```java
@Configuration
@EnableAspectJAutoProxy
public class AppConfig {
}
```

#### 使用 XML 方式使能@AspectJ

```
<aop:aspectj-autoproxy/>
```

### 定义 aspect(切面)

当使用注解 **@Aspect** 标注一个 Bean 后, 那么 Spring 框架会自动收集这些 Bean, 并添加到 Spring AOP 中, 例如:

```java
@Component
@Aspect
public class MyTest {
}
```

`注意, 仅仅使用@Aspect 注解, 并不能将一个 Java 对象转换为 Bean, 因此我们还需要使用类似 @Component 之类的注解.`
`注意, 如果一个 类被@Aspect 标注, 则这个类就不能是其他 aspect 的 **advised object** 了, 因为使用 @Aspect 后, 这个类就会被排除在 auto-proxying 机制之外.`

### 声明 pointcut

一个 pointcut 的声明由两部分组成:

- 一个方法签名, 包括方法名和相关参数
- 一个 pointcut 表达式, 用来指定哪些方法执行是我们感兴趣的(即因此可以织入 advice).

在@AspectJ 风格的 AOP 中, 我们使用一个方法来描述 pointcut, 即:

```java
@Pointcut("execution(* com.xys.service.UserService.*(..))") // 切点表达式
private void dataAccessOperation() {} // 切点前面
```

`这个方法必须无返回值.`
`这个方法本身就是 pointcut signature, pointcut 表达式使用@Pointcut 注解指定.`
上面我们简单地定义了一个 pointcut, 这个 pointcut 所描述的是: 匹配所有在包 **com.xys.service.UserService** 下的所有方法的执行.

#### 切点标志符(designator)

AspectJ5 的切点表达式由标志符(designator)和操作参数组成. 如 "execution(* greetTo(..))" 的切点表达式, \**execution** 就是 标志符, 而圆括号里的 *****greetTo(..) 就是操作参数

##### execution

匹配 join point 的执行, 例如 "execution(* hello(..))" 表示匹配所有目标类中的 hello() 方法. 这个是最基本的 pointcut 标志符.

##### within

匹配特定包下的所有 join point, 例如 `within(com.xys.*)` 表示 com.xys 包中的所有连接点, 即包中的所有类的所有方法. 而`within(com.xys.service.*Service)` 表示在 com.xys.service 包中所有以 Service 结尾的类的所有的连接点.

##### this 与 target

this 的作用是匹配一个 bean, 这个 bean(Spring AOP proxy) 是一个给定类型的实例(instance of). 而 target 匹配的是一个目标对象(target object, 即需要织入 advice 的原始的类), 此对象是一个给定类型的实例(instance of).

##### bean

匹配 bean 名字为指定值的 bean 下的所有方法, 例如:

```
bean(*Service) // 匹配名字后缀为 Service 的 bean 下的所有方法
bean(myService) // 匹配名字为 myService 的 bean 下的所有方法
```

##### args

匹配参数满足要求的的方法.
例如:

```java
@Pointcut("within(com.xys.demo2.*)")
public void pointcut2() {
}

@Before(value = "pointcut2()  &&  args(name)")
public void doSomething(String name) {
    logger.info("---page: {}---", name);
}
```

```java
@Service
public class NormalService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public void someMethod() {
        logger.info("---NormalService: someMethod invoked---");
    }

    public String test(String name) {
        logger.info("---NormalService: test invoked---");
        return "服务一切正常";
    }
}
```

当 NormalService.test 执行时, 则 advice `doSomething` 就会执行, test 方法的参数 name 就会传递到 `doSomething` 中.

常用例子:

```java
// 匹配只有一个参数 name 的方法
@Before(value = "aspectMethod()  &&  args(name)")
public void doSomething(String name) {
}

// 匹配第一个参数为 name 的方法
@Before(value = "aspectMethod()  &&  args(name, ..)")
public void doSomething(String name) {
}

// 匹配第二个参数为 name 的方法
Before(value = "aspectMethod()  &&  args(*, name, ..)")
public void doSomething(String name) {
}
```

##### @annotation

匹配由指定注解所标注的方法, 例如:

```java
@Pointcut("@annotation(com.xys.demo1.AuthChecker)")
public void pointcut() {
}
```

则匹配由注解 `AuthChecker` 所标注的方法.

#### 常见的切点表达式

##### 匹配方法签名

```
// 匹配指定包中的所有的方法
execution(* com.xys.service.*(..))

// 匹配当前包中的指定类的所有方法
execution(* UserService.*(..))

// 匹配指定包中的所有 public 方法
execution(public * com.xys.service.*(..))

// 匹配指定包中的所有 public 方法, 并且返回值是 int 类型的方法
execution(public int com.xys.service.*(..))

// 匹配指定包中的所有 public 方法, 并且第一个参数是 String, 返回值是 int 类型的方法
execution(public int com.xys.service.*(String name, ..))
```

##### 匹配类型签名

```
// 匹配指定包中的所有的方法, 但不包括子包
within(com.xys.service.*)

// 匹配指定包中的所有的方法, 包括子包
within(com.xys.service..*)

// 匹配当前包中的指定类中的方法
within(UserService)


// 匹配一个接口的所有实现类中的实现的方法
within(UserDao+)
```

##### 匹配 Bean 名字

```
// 匹配以指定名字结尾的 Bean 中的所有方法
bean(*Service)
```

##### 切点表达式组合

```
// 匹配以 Service 或 ServiceImpl 结尾的 bean
bean(*Service || *ServiceImpl)

// 匹配名字以 Service 结尾, 并且在包 com.xys.service 中的 bean
bean(*Service) && within(com.xys.service.*)
```

### 声明 advice

advice 是和一个 pointcut 表达式关联在一起的, 并且会在匹配的 join point 的方法执行的前/后/周围 运行. `pointcut 表达式可以是简单的一个 pointcut 名字的引用, 或者是完整的 pointcut 表达式`.
下面我们以几个简单的 advice 为例子, 来看一下一个 advice 是如何声明的.

#### Before advice

```java
/**
 * @author xiongyongshun
 * @version 1.0
 * @created 16/9/9 13:13
 */
@Component
@Aspect
public class BeforeAspectTest {
    // 定义一个 Pointcut, 使用 切点表达式函数 来描述对哪些 Join point 使用 advise.
    @Pointcut("execution(* com.xys.service.UserService.*(..))")
    public void dataAccessOperation() {
    }
}
```

```java
@Component
@Aspect
public class AdviseDefine {
    // 定义 advise
    @Before("com.xys.aspect.PointcutDefine.dataAccessOperation()")
    public void doBeforeAccessCheck(JoinPoint joinPoint) {
        System.out.println("*****Before advise, method: " + joinPoint.getSignature().toShortString() + " *****");
    }
}
```

这里, **@Before** 引用了一个 pointcut, 即 "com.xys.aspect.PointcutDefine.dataAccessOperation()" 是一个 pointcut 的名字.
如果我们在 advice 在内置 pointcut, 则可以:

```java
@Component
@Aspect
public class AdviseDefine {
    // 将 pointcut 和 advice 同时定义
    @Before("within(com.xys.service..*)")
    public void doAccessCheck(JoinPoint joinPoint) {
        System.out.println("*****doAccessCheck, Before advise, method: " + joinPoint.getSignature().toShortString() + " *****");
    }
}
```

#### around advice

around advice 比较特别, 它可以在一个方法的之前之前和之后添加不同的操作, 并且甚至可以决定何时, 如何, 是否调用匹配到的方法.

```java
@Component
@Aspect
public class AdviseDefine {
    // 定义 advise
    @Around("com.xys.aspect.PointcutDefine.dataAccessOperation()")
    public Object doAroundAccessCheck(ProceedingJoinPoint pjp) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // 开始
        Object retVal = pjp.proceed();
        stopWatch.stop();
        // 结束
        System.out.println("invoke method: " + pjp.getSignature().getName() + ", elapsed time: " + stopWatch.getTotalTimeMillis());
        return retVal;
    }
}
```

around advice 和前面的 before advice 差不多, 只是我们把注解 **@Before** 改为了 **@Around** 了.
