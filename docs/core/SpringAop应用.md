# Spring Aop 应用

<!-- TOC depthFrom:2 depthTo:3 -->

<!-- /TOC -->

引入 jar 包

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-aop</artifactId>
  <version>2.2.1.RELEASE</version>
</dependency>
```

- @Pointcut
- 前置通知（@Before）：在目标方法被调用之前调用通知功能；
- 后置通知（@After）：在目标方法完成之后调用通知，此时不会关心方法的输出是什么；
- 返回通知（@AfterReturning）：在目标方法成功执行之后调用通知；
- 异常通知（@AfterThrowing）：在目标方法抛出异常后调用通知。
- @Around

## 参考资料

- [《 Spring 实战（第 4 版）》](https://item.jd.com/11899370.html)
