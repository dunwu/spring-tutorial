# 依赖注入

## 一、依赖注入模式和类型

### 依赖注入模式

依赖注入模式可以分为手动模式和自动模式

#### 手动注入

配置或者编程的方式，提前安排注入规则

- XML 资源配置元信息
- Java 注解配置元信息
- API 配置元信息

#### 自动注入

实现方提供依赖自动关联的方式，按照內建的注入规则（官方不推荐）

- Autowiring（自动绑定）

| 模式        | 说明                                                                   |
| ----------- | ---------------------------------------------------------------------- |
| no          | 默认值，未激活 Autowiring，需要手动指定依赖注入对象。                  |
| byName      | 根据被注入属性的名称作为 Bean 名称进行依赖查找，并将对象设置到该属性。 |
| byType      | 根据被注入属性的类型作为依赖类型进行查找，并将对象设置到该属性。       |
| constructor | 特殊 byType 类型，用于构造器参数。                                     |

> 限制和不足参考：[Limitations and Disadvantages of Autowiring 小节](https://docs.spring.io/spring/docs/5.2.2.RELEASE/spring-frameworkreference/core.html#beans-autowired-exceptions)

### 依赖注入类型

| 依赖注入类型 | 配置元数据举例                                     |
| ------------ | -------------------------------------------------- |
| Setter 方法  | `<proeprty name="user" ref="userBean"/>`           |
| 构造器       | `<constructor-arg name="user" ref="userBean" />`   |
| 字段         | `@Autowired User user;`                            |
| 方法         | `@Autowired public void user(User user) { ... }`   |
| 接口回调     | `class MyBean implements BeanFactoryAware { ... }` |

#### Setter 方法注入

手动模式

- XML 资源配置元信息
- Java 注解配置元信息
- API 配置元信息

自动模式

- byName
- byType

#### 构造器注入

手动模式

- XML 资源配置元信息
- Java 注解配置元信息
- API 配置元信息

自动模式

- constructor

#### 字段注入

手动模式（Java 注解配置元信息）

- `@Autowired`
- `@Resource`
- `@Inject`（可选）

#### 方法注入

手动模式（Java 注解配置元信息）

- `@Autowired`
- `@Resource`
- `@Inject`（可选）
- `@Bean`

#### 接口回调注入

Aware 系列接口回调

| 內建接口                         | 说明                                                       |
| -------------------------------- | ---------------------------------------------------------- |
| `BeanFactoryAware`               | 获取 IoC 容器- `BeanFactory`                               |
| `ApplicationContextAware`        | 获取 Spring 应用上下文- `ApplicationContext` 对象          |
| `EnvironmentAware`               | 获取 `Environment` 对象                                    |
| `ResourceLoaderAware`            | 获取资源加载器对象- `ResourceLoader`                       |
| `BeanClassLoaderAware`           | 获取加载当前 Bean Class 的 `ClassLoader`                   |
| `BeanNameAware`                  | 获取当前 Bean 的名称                                       |
| `MessageSourceAware`             | 获取 `MessageSource` 对象，用于 Spring 国际化              |
| `ApplicationEventPublisherAware` | 获取 `ApplicationEventPublishAware` 对象，用于 Spring 事件 |
| `EmbeddedValueResolverAware`     | 获取 `StringValueResolver` 对象，用于占位符处理            |

#### 依赖注入类型选择

- 低依赖：构造器注入
- 多依赖：Setter 方法注入
- 便利性：字段注入
- 声明类：方法注入

## 自动绑定

## 二、被注入的数据类型

基础类型

- 原生类型（Primitive）：boolean、byte、char、short、int、float、long、double
- 标量类型（Scalar）：Number、Character、Boolean、Enum、Locale、Charset、Currency、Properties、UUID
- 常规类型（General）：Object、String、TimeZone、Calendar、Optional 等
- Spring 类型：Resource、InputSource、Formatter 等

集合类型

- 数组类型（Array）：原生类型、标量类型、常规类型、Spring 类型
- 集合类型（Collection）
  - Collection：List、Set（SortedSet、NavigableSet、EnumSet）
  - Map：Properties

## 三、依赖注入辅助功能

### 限定注入

使用注解@Qualifier 限定

- 通过 Bean 名称限定
- 通过分组限定

基于注解@Qualifier 扩展限定

- 自定义注解- 如 Spring Cloud @LoadBalanced

### 延迟依赖注入

使用 API ObjectFactory 延迟注入

- 单一类型
- 集合类型

使用 API ObjectProvider 延迟注入（推荐）

- 单一类型
- 集合类型

## 四、@Autowired 和 @Inject

### @Autowired 注入

@Autowired 注入过程

- 元信息解析
- 依赖查找
- 依赖注入（字段、方法）

### @Inject 注入

@Inject 注入过程

如果 JSR-330 存在于 ClassPath 中，复用 AutowiredAnnotationBeanPostProcessor 实现

## 四、依赖处理过程

- 入口- DefaultListableBeanFactory#resolveDependency
- 依赖描述符- DependencyDescriptor
- 自定绑定候选对象处理器- AutowireCandidateResolver

## Java 通用注解注入原理

CommonAnnotationBeanPostProcessor

注入注解

- javax.xml.ws.WebServiceRef
- javax.ejb.EJB
- javax.annotation.Resource

生命周期注解

- javax.annotation.PostConstruct
- javax.annotation.PreDestroy

## 自定义依赖注入注解

基于 AutowiredAnnotationBeanPostProcessor 实现

生命周期处理

- InstantiationAwareBeanPostProcessor
- MergedBeanDefinitionPostProcessor

元数据

- InjectedElement
- InjectionMetadata
