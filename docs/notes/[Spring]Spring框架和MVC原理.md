# Spring框架

Spring当前框架有**20**个jar包，大致可以分为**6**大模块:

- Core Container
- AOP and Instrumentation
- Messaging
- Data Access/Integration
- Web
- Test

Spring框架提供了非常丰富的功能，因此整个架构也很庞大。
在我们实际的应用开发中，并不一定要使用所有的功能，而是可以根据需要选择合适的Spring模块。
![Paste_Image.png](http://upload-images.jianshu.io/upload_images/3101171-47b048e2e94886be.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 核心容器 (Core Container)
IoC容器是Spring框架的核心。spring容器使用依赖注入管理构成应用的组件，它会创建相互协作的组件之间的关联。毫无疑问，这些对象更简单干净，更容易理解，也更容易重用和测试。
Spring自带了几种容器的实现，可归纳为两种类型：

### BeanFactory
由org.springframework.beans.factory.BeanFactory接口定义。
它是最简单的容器，提供基本的DI支持。 

### ApplicationContext
由org.springframework.context.ApplicationContext接口定义。
它是基于BeanFactory 之上构建，并提供面向应用的服务，例如从属性文件解析文本信息的能力，以及发布应用事件给感兴趣的事件监听者的能力。 
***注：Bean工厂对于大多数应用来说往往太低级了，所以应用上下文使用更广泛。推荐在开发中使用应用上下文容器。***

Spring自带了多种应用上下文，最可能遇到的有以下几种：
`ClassPathXmlApplicationContext`：从类路径下的XML配置文件中加载上下文定义，把应用上下文定义文件当做类资源。
`FileSystemXmlApplicationContext`：读取文件系统下的XML配置文件并加载上下文定义。
`XmlWebApplicationContext`：读取Web应用下的XML配置文件并装载上下文定义。

***范例***
```java
ApplicationContext context = new FileSystemXmlApplicationContext("D:\Temp\build.xml");
ApplicationContext context2 = new ClassPathXmlApplicationContext("build.xml");
```
可以看到，加载 `FileSystemXmlApplicationContext` 和 `ClassPathXmlApplicationContext` 十分相似。
差异在于：前者在指定文件系统路径下查找build.xml文件；而后在所有类路径（包含JAR文件）下查找build.xml文件。
通过引用应用上下文，可以很方便的调用 getBean() 方法从 Spring 容器中获取 Bean。 

**相关jar包**
`spring-core`, `spring-beans`, 提供框架的基础部分，包括IoC和依赖注入特性。
`spring-context`, 在`spring-core`, `spring-beans`基础上构建。它提供一种框架式的访问对象的方法。
它也支持类似Java EE特性，例如：EJB，JMX和基本remoting。ApplicationContext接口是它的聚焦点。
`springcontext-support`, 集成第三方库到Spring application context。
`spring-expression`，提供一种强有力的表达语言在运行时来查询和操纵一个对象图。

## AOP 和 Instrumentation
**相关jar包**
`spring-aop`，提供了对面向切面编程的丰富支持。
`spring-aspects`，提供了对AspectJ的集成。
`spring-instrument`，提供了对类instrumentation的支持和类加载器。
`spring-instrument-tomcat`，包含了Spring对Tomcat的instrumentation代理。

## Messaging
**相关jar包**
`spring-messaging`，包含spring的消息处理功能，如Message，MessageChannel，MessageHandler。

## 数据访问与集成 (Data Access / Integaration)
Data Access/Integration层包含了JDBC / ORM / OXM / JMS和Transaction模块。
**相关jar包**
`spring-jdbc`，提供了一个JDBC抽象层。
`spring-tx`，支持编程和声明式事务管理类。
`spring-orm`，提供了流行的对象关系型映射API集，如JPA，JDO，Hibernate。
`spring-oxm`，提供了一个抽象层以支持对象/XML 映射的实现，如JAXB，Castor，XMLBeans，JiBX 和 XStream.
`spring-jms`，包含了生产和消费消息的功能。

## Web
**相关jar包**
`spring-web`，提供了基本的面向web的功能，如多文件上传、使用Servlet监听器的Ioc容器的初始化。一个面向web的应用层上下文。
`spring-webmvc`，包括MVC和REST web服务实现。
`spring-webmvc-portlet`，提供在Protlet环境的MVC实现和`spring-webmvc`功能的镜像。

## 测试 (Test)
**相关jar包**
`spring-test`，以Junit和TestNG来支持spring组件的单元测试和集成测试。

# SpringMVC工作流程描述
Spring MVC的工作流程可以用一幅图来说明：
![Paste_Image.png](http://upload-images.jianshu.io/upload_images/3101171-e6964e6128cf7ae1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
（1）向服务器发送HTTP请求，请求被前端控制器 `DispatcherServlet` 捕获。
（2）`DispatcherServlet` 根据 **<servlet-name>-servlet.xml** 中的配置对请求的URL进行解析，得到请求资源标识符（URI）。
然后根据该URI，调用 `HandlerMapping` 获得该Handler配置的所有相关的对象（包括Handler对象以及Handler对象对应的拦截器），最后以`HandlerExecutionChain` 对象的形式返回。
（3）`DispatcherServlet` 根据获得的`Handler`，选择一个合适的 `HandlerAdapter`。（附注：如果成功获得`HandlerAdapter`后，此时将开始执行拦截器的preHandler(...)方法）。
（4）提取`Request`中的模型数据，填充`Handler`入参，开始执行`Handler`（`Controller`)。 在填充`Handler`的入参过程中，根据你的配置，Spring将帮你做一些额外的工作：
HttpMessageConveter： 将请求消息（如Json、xml等数据）转换成一个对象，将对象转换为指定的响应信息。
数据转换：对请求消息进行数据转换。如`String`转换成`Integer`、`Double`等。
数据根式化：对请求消息进行数据格式化。 如将字符串转换成格式化数字或格式化日期等。
数据验证： 验证数据的有效性（长度、格式等），验证结果存储到`BindingResult`或`Error`中。
（5）Handler(Controller)执行完成后，向 `DispatcherServlet` 返回一个 `ModelAndView` 对象；
（6）根据返回的`ModelAndView`，选择一个适合的 `ViewResolver`（必须是已经注册到Spring容器中的`ViewResolver`)返回给`DispatcherServlet`。
（7）`ViewResolver` 结合`Model`和`View`，来渲染视图。
（8）视图负责将渲染结果返回给客户端。