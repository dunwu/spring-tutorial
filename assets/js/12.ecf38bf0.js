(window.webpackJsonp=window.webpackJsonp||[]).push([[12],{350:function(t,e,v){"use strict";v.r(e);var _=v(4),n=Object(_.a)({},(function(){var t=this,e=t._self._c;return e("ContentSlotsDistributor",{attrs:{"slot-key":t.$parent.slotKey}},[e("h1",{attrs:{id:"spring-依赖查找"}},[e("a",{staticClass:"header-anchor",attrs:{href:"#spring-依赖查找"}},[t._v("#")]),t._v(" Spring 依赖查找")]),t._v(" "),e("p",[e("strong",[t._v("依赖查找是主动或手动的依赖查找方式，通常需要依赖容器或标准 API 实现")]),t._v("。")]),t._v(" "),e("p",[t._v("IoC 依赖查找大致可以分为以下几类：")]),t._v(" "),e("ul",[e("li",[t._v("根据 Bean 名称查找")]),t._v(" "),e("li",[t._v("根据 Bean 类型查找")]),t._v(" "),e("li",[t._v("根据 Bean 名称 + 类型查找")]),t._v(" "),e("li",[t._v("根据 Java 注解查找")])]),t._v(" "),e("p",[t._v("此外，根据查找的 Bean 对象是单一或集合对象，是否需要延迟查找等特定常见，有相应不同的 API。")]),t._v(" "),e("h2",{attrs:{id:"单一类型依赖查找"}},[e("a",{staticClass:"header-anchor",attrs:{href:"#单一类型依赖查找"}},[t._v("#")]),t._v(" 单一类型依赖查找")]),t._v(" "),e("p",[t._v("单一类型依赖查找接口- "),e("code",[t._v("BeanFactory")])]),t._v(" "),e("ul",[e("li",[t._v("根据 Bean 名称查找\n"),e("ul",[e("li",[e("code",[t._v("getBean(String)")])]),t._v(" "),e("li",[t._v("Spring 2.5 覆盖默认参数："),e("code",[t._v("getBean(String,Object...)")])])])]),t._v(" "),e("li",[t._v("根据 Bean 类型查找\n"),e("ul",[e("li",[t._v("Bean 实时查找\n"),e("ul",[e("li",[t._v("Spring 3.0 "),e("code",[t._v("getBean(Class)")])]),t._v(" "),e("li",[t._v("Spring 4.1 覆盖默认参数："),e("code",[t._v("getBean(Class,Object...)")])])])]),t._v(" "),e("li",[t._v("Spring 5.1 Bean 延迟查找\n"),e("ul",[e("li",[e("code",[t._v("getBeanProvider(Class)")])]),t._v(" "),e("li",[e("code",[t._v("getBeanProvider(ResolvableType)")])])])])])]),t._v(" "),e("li",[t._v("根据 Bean 名称 + 类型查找："),e("code",[t._v("getBean(String,Class)")])])]),t._v(" "),e("h2",{attrs:{id:"集合类型依赖查找"}},[e("a",{staticClass:"header-anchor",attrs:{href:"#集合类型依赖查找"}},[t._v("#")]),t._v(" 集合类型依赖查找")]),t._v(" "),e("p",[t._v("集合类型依赖查找接口- "),e("code",[t._v("ListableBeanFactory")])]),t._v(" "),e("ul",[e("li",[e("p",[t._v("根据 Bean 类型查找")]),t._v(" "),e("ul",[e("li",[t._v("获取同类型 Bean 名称列表\n"),e("ul",[e("li",[e("code",[t._v("getBeanNamesForType(Class)")])]),t._v(" "),e("li",[t._v("Spring 4.2 "),e("code",[t._v("getBeanNamesForType(ResolvableType)")])])])]),t._v(" "),e("li",[t._v("获取同类型 Bean 实例列表\n"),e("ul",[e("li",[e("code",[t._v("getBeansOfType(Class)")]),t._v(" 以及重载方法")])])])])]),t._v(" "),e("li",[e("p",[t._v("通过注解类型查找")]),t._v(" "),e("ul",[e("li",[e("p",[t._v("Spring 3.0 获取标注类型 Bean 名称列表")]),t._v(" "),e("ul",[e("li",[e("code",[t._v("getBeanNamesForAnnotation(Class<? extends Annotation>)")])])])]),t._v(" "),e("li",[e("p",[t._v("Spring 3.0 获取标注类型 Bean 实例列表")]),t._v(" "),e("ul",[e("li",[e("code",[t._v("getBeansWithAnnotation(Class<? extends Annotation>)")])])])]),t._v(" "),e("li",[e("p",[t._v("Spring 3.0 获取指定名称+ 标注类型 Bean 实例")]),t._v(" "),e("ul",[e("li",[e("code",[t._v("findAnnotationOnBean(String,Class<? extends Annotation>)")])])])])])])]),t._v(" "),e("h2",{attrs:{id:"层次性依赖查找"}},[e("a",{staticClass:"header-anchor",attrs:{href:"#层次性依赖查找"}},[t._v("#")]),t._v(" 层次性依赖查找")]),t._v(" "),e("p",[t._v("层次性依赖查找接口- "),e("code",[t._v("HierarchicalBeanFactory")])]),t._v(" "),e("ul",[e("li",[t._v("双亲 "),e("code",[t._v("BeanFactory")]),t._v("："),e("code",[t._v("getParentBeanFactory()")])]),t._v(" "),e("li",[t._v("层次性查找\n"),e("ul",[e("li",[t._v("根据 Bean 名称查找\n"),e("ul",[e("li",[t._v("基于 "),e("code",[t._v("containsLocalBean")]),t._v(" 方法实现")])])]),t._v(" "),e("li",[t._v("根据 Bean 类型查找实例列表\n"),e("ul",[e("li",[t._v("单一类型："),e("code",[t._v("BeanFactoryUtils#beanOfType")])]),t._v(" "),e("li",[t._v("集合类型："),e("code",[t._v("BeanFactoryUtils#beansOfTypeIncludingAncestors")])])])]),t._v(" "),e("li",[t._v("根据 Java 注解查找名称列表\n"),e("ul",[e("li",[e("code",[t._v("BeanFactoryUtils#beanNamesForTypeIncludingAncestors")])])])])])])]),t._v(" "),e("h2",{attrs:{id:"延迟依赖查找"}},[e("a",{staticClass:"header-anchor",attrs:{href:"#延迟依赖查找"}},[t._v("#")]),t._v(" 延迟依赖查找")]),t._v(" "),e("p",[t._v("Bean 延迟依赖查找接口")]),t._v(" "),e("ul",[e("li",[e("code",[t._v("org.springframework.beans.factory.ObjectFactory")])]),t._v(" "),e("li",[e("code",[t._v("org.springframework.beans.factory.ObjectProvider")]),t._v("（Spring 5 对 Java 8 特性扩展）")]),t._v(" "),e("li",[t._v("函数式接口\n"),e("ul",[e("li",[e("code",[t._v("getIfAvailable(Supplier)")])]),t._v(" "),e("li",[e("code",[t._v("ifAvailable(Consumer)")])])])]),t._v(" "),e("li",[t._v("Stream 扩展- stream()")])]),t._v(" "),e("h2",{attrs:{id:"安全依赖查找"}},[e("a",{staticClass:"header-anchor",attrs:{href:"#安全依赖查找"}},[t._v("#")]),t._v(" 安全依赖查找")]),t._v(" "),e("table",[e("thead",[e("tr",[e("th",[t._v("依赖查找类型")]),t._v(" "),e("th",[t._v("代表实现")]),t._v(" "),e("th",[t._v("是否安全")])])]),t._v(" "),e("tbody",[e("tr",[e("td",[t._v("单一类型查找")]),t._v(" "),e("td",[e("code",[t._v("BeanFactory#getBean")])]),t._v(" "),e("td",[t._v("否")])]),t._v(" "),e("tr",[e("td"),t._v(" "),e("td",[e("code",[t._v("ObjectFactory#getObject")])]),t._v(" "),e("td",[t._v("否")])]),t._v(" "),e("tr",[e("td"),t._v(" "),e("td",[e("code",[t._v("ObjectProvider#getIfAvailable")])]),t._v(" "),e("td",[t._v("是")])]),t._v(" "),e("tr",[e("td"),t._v(" "),e("td"),t._v(" "),e("td")]),t._v(" "),e("tr",[e("td",[t._v("集合类型查找")]),t._v(" "),e("td",[e("code",[t._v("ListableBeanFactory#getBeansOfType")])]),t._v(" "),e("td",[t._v("是")])]),t._v(" "),e("tr",[e("td"),t._v(" "),e("td",[e("code",[t._v("ObjectProvider#stream")])]),t._v(" "),e("td",[t._v("是")])])])]),t._v(" "),e("p",[t._v("注意：层次性依赖查找的安全性取决于其扩展的单一或集合类型的 "),e("code",[t._v("BeanFactory")]),t._v(" 接口")]),t._v(" "),e("h2",{attrs:{id:"内建可查找的依赖"}},[e("a",{staticClass:"header-anchor",attrs:{href:"#内建可查找的依赖"}},[t._v("#")]),t._v(" 内建可查找的依赖")]),t._v(" "),e("p",[e("code",[t._v("AbstractApplicationContext")]),t._v(" 内建可查找的依赖")]),t._v(" "),e("table",[e("thead",[e("tr",[e("th",[t._v("Bean")]),t._v(" "),e("th",[t._v("名称 Bean")]),t._v(" "),e("th",[t._v("实例使用场景")])])]),t._v(" "),e("tbody",[e("tr",[e("td",[t._v("environment")]),t._v(" "),e("td",[t._v("Environment 对象")]),t._v(" "),e("td",[t._v("外部化配置以及 Profiles")])]),t._v(" "),e("tr",[e("td",[t._v("systemProperties")]),t._v(" "),e("td",[t._v("java.util.Properties 对象")]),t._v(" "),e("td",[t._v("Java 系统属性")])]),t._v(" "),e("tr",[e("td",[t._v("systemEnvironment")]),t._v(" "),e("td",[t._v("java.util.Map 对象")]),t._v(" "),e("td",[t._v("操作系统环境变量")])]),t._v(" "),e("tr",[e("td",[t._v("messageSource")]),t._v(" "),e("td",[t._v("MessageSource 对象")]),t._v(" "),e("td",[t._v("国际化文案")])]),t._v(" "),e("tr",[e("td",[t._v("lifecycleProcessor")]),t._v(" "),e("td",[t._v("LifecycleProcessor 对象")]),t._v(" "),e("td",[t._v("Lifecycle Bean 处理器")])]),t._v(" "),e("tr",[e("td",[t._v("applicationEventMulticaster")]),t._v(" "),e("td",[t._v("ApplicationEventMulticaster 对象")]),t._v(" "),e("td",[t._v("Spring 事件广播器")])])])]),t._v(" "),e("p",[t._v("注解驱动 Spring 应用上下文内建可查找的依赖（部分）")]),t._v(" "),e("table",[e("thead",[e("tr",[e("th",[t._v("Bean 名称")]),t._v(" "),e("th",[t._v("Bean 实例")]),t._v(" "),e("th",[t._v("使用场景")])])]),t._v(" "),e("tbody",[e("tr",[e("td",[t._v("org.springframework.context.annotation.internalConfigurationAnnotationProcessor")]),t._v(" "),e("td",[t._v("ConfigurationClassPostProcessor 对象")]),t._v(" "),e("td",[t._v("处理 Spring 配置类")])]),t._v(" "),e("tr",[e("td",[t._v("org.springframework.context.annotation.internalAutowiredAnnotationProcessor")]),t._v(" "),e("td",[t._v("AutowiredAnnotationBeanPostProcessor 对象")]),t._v(" "),e("td",[t._v("处理@Autowired 以及@Value 注解")])]),t._v(" "),e("tr",[e("td",[t._v("org.springframework.context.annotation.internalCommonAnnotationProcessor")]),t._v(" "),e("td",[t._v("CommonAnnotationBeanPostProcessor 对象")]),t._v(" "),e("td",[t._v("（条件激活）处理 JSR-250 注解，如@PostConstruct 等")])]),t._v(" "),e("tr",[e("td",[t._v("org.springframework.context.event.internalEventListenerProcessor")]),t._v(" "),e("td",[t._v("EventListenerMethodProcessor 对象")]),t._v(" "),e("td",[t._v("处理标注@EventListener 的 Spring 事件监听方法")])]),t._v(" "),e("tr",[e("td",[t._v("org.springframework.context.event.internalEventListenerFactory")]),t._v(" "),e("td",[t._v("DefaultEventListenerFactory 对象")]),t._v(" "),e("td",[t._v("@EventListener 事件监听方法适配为 ApplicationListener")])]),t._v(" "),e("tr",[e("td",[t._v("org.springframework.context.annotation.internalPersistenceAnnotationProcessor")]),t._v(" "),e("td",[t._v("PersistenceAnnotationBeanPostProcessor 对象")]),t._v(" "),e("td",[t._v("（条件激活）处理 JPA 注解场景")])])])]),t._v(" "),e("h2",{attrs:{id:"依赖查找中的经典异常"}},[e("a",{staticClass:"header-anchor",attrs:{href:"#依赖查找中的经典异常"}},[t._v("#")]),t._v(" 依赖查找中的经典异常")]),t._v(" "),e("p",[e("code",[t._v("BeansException")]),t._v(" 子类型")]),t._v(" "),e("table",[e("thead",[e("tr",[e("th",[t._v("异常类型")]),t._v(" "),e("th",[t._v("触发条件（举例）")]),t._v(" "),e("th",[t._v("场景举例")])])]),t._v(" "),e("tbody",[e("tr",[e("td",[e("code",[t._v("NoSuchBeanDefinitionException")])]),t._v(" "),e("td",[t._v("当查找 Bean 不存在于 IoC 容器时")]),t._v(" "),e("td",[e("code",[t._v("BeanFactory#getBeanObjectFactory#getObject")])])]),t._v(" "),e("tr",[e("td",[e("code",[t._v("NoUniqueBeanDefinitionException")])]),t._v(" "),e("td",[t._v("类型依赖查找时，IoC 容器存在多个 Bean 实例")]),t._v(" "),e("td",[e("code",[t._v("BeanFactory#getBean(Class)")])])]),t._v(" "),e("tr",[e("td",[e("code",[t._v("BeanInstantiationException")])]),t._v(" "),e("td",[t._v("当 Bean 所对应的类型非具体类时")]),t._v(" "),e("td",[e("code",[t._v("BeanFactory#getBean")])])]),t._v(" "),e("tr",[e("td",[e("code",[t._v("BeanCreationException")])]),t._v(" "),e("td",[t._v("当 Bean 初始化过程中")]),t._v(" "),e("td",[t._v("Bean 初始化方法执行异常时")])]),t._v(" "),e("tr",[e("td",[e("code",[t._v("BeanDefinitionStoreException")])]),t._v(" "),e("td",[t._v("当 "),e("code",[t._v("BeanDefinition")]),t._v(" 配置元信息非法时")]),t._v(" "),e("td",[t._v("XML 配置资源无法打开时")])])])]),t._v(" "),e("h2",{attrs:{id:"参考资料"}},[e("a",{staticClass:"header-anchor",attrs:{href:"#参考资料"}},[t._v("#")]),t._v(" 参考资料")]),t._v(" "),e("ul",[e("li",[e("a",{attrs:{href:"https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#beans",target:"_blank",rel:"noopener noreferrer"}},[t._v("Spring 官方文档之 Core Technologies"),e("OutboundLink")],1)]),t._v(" "),e("li",[e("a",{attrs:{href:"https://time.geekbang.org/course/intro/265",target:"_blank",rel:"noopener noreferrer"}},[t._v("《小马哥讲 Spring 核心编程思想》"),e("OutboundLink")],1)])])])}),[],!1,null,null,null);e.default=n.exports}}]);