(window.webpackJsonp=window.webpackJsonp||[]).push([[15],{353:function(t,a,s){"use strict";s.r(a);var e=s(4),n=Object(e.a)({},(function(){var t=this,a=t._self._c;return a("ContentSlotsDistributor",{attrs:{"slot-key":t.$parent.slotKey}},[a("h1",{attrs:{id:"spring-bean-作用域"}},[a("a",{staticClass:"header-anchor",attrs:{href:"#spring-bean-作用域"}},[t._v("#")]),t._v(" Spring Bean 作用域")]),t._v(" "),a("h2",{attrs:{id:"spring-bean-作用域-2"}},[a("a",{staticClass:"header-anchor",attrs:{href:"#spring-bean-作用域-2"}},[t._v("#")]),t._v(" Spring Bean 作用域")]),t._v(" "),a("table",[a("thead",[a("tr",[a("th",[t._v("来源")]),t._v(" "),a("th",[t._v("说明")])])]),t._v(" "),a("tbody",[a("tr",[a("td",[t._v("singleton")]),t._v(" "),a("td",[t._v("默认 Spring Bean 作用域，一个 BeanFactory 有且仅有一个实例")])]),t._v(" "),a("tr",[a("td",[t._v("prototype")]),t._v(" "),a("td",[t._v("原型作用域，每次依赖查找和依赖注入生成新 Bean 对象")])]),t._v(" "),a("tr",[a("td",[t._v("request")]),t._v(" "),a("td",[t._v("将 Spring Bean 存储在 ServletRequest 上下文中")])]),t._v(" "),a("tr",[a("td",[t._v("session")]),t._v(" "),a("td",[t._v("将 Spring Bean 存储在 HttpSession 中")])]),t._v(" "),a("tr",[a("td",[t._v("application")]),t._v(" "),a("td",[t._v("将 Spring Bean 存储在 ServletContext 中")])])])]),t._v(" "),a("h2",{attrs:{id:"singleton-bean-作用域"}},[a("a",{staticClass:"header-anchor",attrs:{href:"#singleton-bean-作用域"}},[t._v("#")]),t._v(' "singleton" Bean 作用域')]),t._v(" "),a("p",[a("img",{attrs:{src:"https://raw.githubusercontent.com/dunwu/images/master/snap/20221221170833.png",alt:""}})]),t._v(" "),a("h2",{attrs:{id:"prototype-bean-作用域"}},[a("a",{staticClass:"header-anchor",attrs:{href:"#prototype-bean-作用域"}},[t._v("#")]),t._v(' "prototype" Bean 作用域')]),t._v(" "),a("p",[t._v("Spring 容器没有办法管理 prototype Bean 的完整生命周期，也没有办法记录实例的存在。销毁回调方法将不会执行，可以利用 "),a("code",[t._v("BeanPostProcessor")]),t._v(" 进行清扫工作。")]),t._v(" "),a("h2",{attrs:{id:"request-bean-作用域"}},[a("a",{staticClass:"header-anchor",attrs:{href:"#request-bean-作用域"}},[t._v("#")]),t._v(' "request" Bean 作用域')]),t._v(" "),a("ul",[a("li",[t._v("配置\n"),a("ul",[a("li",[t._v("XML - "),a("code",[t._v('<bean class="..." scope = “request" />')])]),t._v(" "),a("li",[t._v("Java 注解 - "),a("code",[t._v("@RequestScope")]),t._v(" 或 "),a("code",[t._v("@Scope(WebApplicationContext.SCOPE_REQUEST)")])])])]),t._v(" "),a("li",[t._v("实现\n"),a("ul",[a("li",[t._v("API - RequestScope")])])])]),t._v(" "),a("h2",{attrs:{id:"session-bean-作用域"}},[a("a",{staticClass:"header-anchor",attrs:{href:"#session-bean-作用域"}},[t._v("#")]),t._v(' "session" Bean 作用域')]),t._v(" "),a("ul",[a("li",[t._v("配置\n"),a("ul",[a("li",[t._v("XML - "),a("code",[t._v('<bean class="..." scope = “session" />')])]),t._v(" "),a("li",[t._v("Java 注解 - "),a("code",[t._v("@SessionScope")]),t._v(" 或 "),a("code",[t._v("@Scope(WebApplicationContext.SCOPE_SESSION)")])])])]),t._v(" "),a("li",[t._v("实现\n"),a("ul",[a("li",[t._v("API - SessionScope")])])])]),t._v(" "),a("h2",{attrs:{id:"application-bean-作用域"}},[a("a",{staticClass:"header-anchor",attrs:{href:"#application-bean-作用域"}},[t._v("#")]),t._v(' "application" Bean 作用域')]),t._v(" "),a("ul",[a("li",[t._v("配置\n"),a("ul",[a("li",[t._v("XML - "),a("code",[t._v('<bean class="..." scope = “application" />')])]),t._v(" "),a("li",[t._v("Java 注解 - "),a("code",[t._v("@ApplicationScope")]),t._v(" 或 "),a("code",[t._v("@Scope(WebApplicationContext.SCOPE_APPLICATION)")])])])]),t._v(" "),a("li",[t._v("实现\n"),a("ul",[a("li",[t._v("API - ServletContextScope")])])])]),t._v(" "),a("h2",{attrs:{id:"自定义-bean-作用域"}},[a("a",{staticClass:"header-anchor",attrs:{href:"#自定义-bean-作用域"}},[t._v("#")]),t._v(" 自定义 Bean 作用域")]),t._v(" "),a("ul",[a("li",[a("p",[t._v("实现 Scope")]),t._v(" "),a("ul",[a("li",[a("code",[t._v("org.springframework.beans.factory.config.Scope")])])])]),t._v(" "),a("li",[a("p",[t._v("注册 Scope")]),t._v(" "),a("ul",[a("li",[t._v("API - "),a("code",[t._v("org.springframework.beans.factory.config.ConfigurableBeanFactory#registerScope")])])])]),t._v(" "),a("li",[a("p",[t._v("配置")]),t._v(" "),a("div",{staticClass:"language-xml extra-class"},[a("pre",{pre:!0,attrs:{class:"language-xml"}},[a("code",[a("span",{pre:!0,attrs:{class:"token tag"}},[a("span",{pre:!0,attrs:{class:"token tag"}},[a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v("<")]),t._v("bean")]),t._v(" "),a("span",{pre:!0,attrs:{class:"token attr-name"}},[t._v("class")]),a("span",{pre:!0,attrs:{class:"token attr-value"}},[a("span",{pre:!0,attrs:{class:"token punctuation attr-equals"}},[t._v("=")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v('"')]),t._v("org.springframework.beans.factory.config.CustomScopeConfigurer"),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v('"')])]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(">")])]),t._v("\n  "),a("span",{pre:!0,attrs:{class:"token tag"}},[a("span",{pre:!0,attrs:{class:"token tag"}},[a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v("<")]),t._v("property")]),t._v(" "),a("span",{pre:!0,attrs:{class:"token attr-name"}},[t._v("name")]),a("span",{pre:!0,attrs:{class:"token attr-value"}},[a("span",{pre:!0,attrs:{class:"token punctuation attr-equals"}},[t._v("=")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v('"')]),t._v("scopes"),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v('"')])]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(">")])]),t._v("\n    "),a("span",{pre:!0,attrs:{class:"token tag"}},[a("span",{pre:!0,attrs:{class:"token tag"}},[a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v("<")]),t._v("map")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(">")])]),t._v("\n      "),a("span",{pre:!0,attrs:{class:"token tag"}},[a("span",{pre:!0,attrs:{class:"token tag"}},[a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v("<")]),t._v("entry")]),t._v(" "),a("span",{pre:!0,attrs:{class:"token attr-name"}},[t._v("key")]),a("span",{pre:!0,attrs:{class:"token attr-value"}},[a("span",{pre:!0,attrs:{class:"token punctuation attr-equals"}},[t._v("=")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v('"')]),t._v("..."),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v('"')])]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(">")])]),t._v("\n      "),a("span",{pre:!0,attrs:{class:"token tag"}},[a("span",{pre:!0,attrs:{class:"token tag"}},[a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v("</")]),t._v("entry")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(">")])]),t._v("\n    "),a("span",{pre:!0,attrs:{class:"token tag"}},[a("span",{pre:!0,attrs:{class:"token tag"}},[a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v("</")]),t._v("map")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(">")])]),t._v("\n  "),a("span",{pre:!0,attrs:{class:"token tag"}},[a("span",{pre:!0,attrs:{class:"token tag"}},[a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v("</")]),t._v("property")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(">")])]),t._v("\n"),a("span",{pre:!0,attrs:{class:"token tag"}},[a("span",{pre:!0,attrs:{class:"token tag"}},[a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v("</")]),t._v("bean")]),a("span",{pre:!0,attrs:{class:"token punctuation"}},[t._v(">")])]),t._v("\n")])])])])]),t._v(" "),a("h2",{attrs:{id:"问题"}},[a("a",{staticClass:"header-anchor",attrs:{href:"#问题"}},[t._v("#")]),t._v(" 问题")]),t._v(" "),a("p",[t._v("Spring 內建的 Bean 作用域有几种？")]),t._v(" "),a("p",[t._v("singleton、prototype、request、session、application 以及 websocket")]),t._v(" "),a("p",[t._v("singleton Bean 是否在一个应用是唯一的？")]),t._v(" "),a("p",[t._v("否。singleton bean 仅在当前 Spring IoC 容器（BeanFactory）中是单例对象。")]),t._v(" "),a("p",[t._v("application Bean 是否可以被其他方案替代？")]),t._v(" "),a("p",[t._v("可以的，实际上，“application” Bean 与“singleton” Bean 没有本质区别")]),t._v(" "),a("h2",{attrs:{id:"参考资料"}},[a("a",{staticClass:"header-anchor",attrs:{href:"#参考资料"}},[t._v("#")]),t._v(" 参考资料")]),t._v(" "),a("ul",[a("li",[a("a",{attrs:{href:"https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#beans",target:"_blank",rel:"noopener noreferrer"}},[t._v("Spring 官方文档之 Core Technologies"),a("OutboundLink")],1)]),t._v(" "),a("li",[a("a",{attrs:{href:"https://time.geekbang.org/course/intro/265",target:"_blank",rel:"noopener noreferrer"}},[t._v("《小马哥讲 Spring 核心编程思想》"),a("OutboundLink")],1)])])])}),[],!1,null,null,null);a.default=n.exports}}]);