# spring-boot-web-https

> 本项目展示 SpringBoot 如何提供一个 [HTTPS](https://en.wikipedia.org/wiki/HTTPS) 服务示例。
>
> 本项目的功能几乎和 [spring-boot-web-helloworld](https://github.com/dunwu/spring-tutorial/tree/master/codes/web/spring-boot-web-helloworld) 完全相同，只是支持的 HTTP 协议不同。

## 数字证书

部署 HTTPS web 应用，首先需要有一个 SSL 证书（SSL Certificates）。

数字证书是要收费的，可以在阿里云、腾讯云等云服务商或证书授权机构购买。

还有一种方式是通过 JDK 自带的 keytool 工具生成一个私有证书。

进入到 `%JAVVA_HOME%\bin` 目录下，执行如下命令生成一个数字证书：

```shell
keytool -genkey -alias tomcat -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore keystore.p12 -validity 3650
输入密钥库口令:
再次输入新口令:
您的名字与姓氏是什么?
  [Unknown]:
您的组织单位名称是什么?
  [Unknown]:
您的组织名称是什么?
  [Unknown]:
您所在的城市或区域名称是什么?
  [Unknown]:
您所在的省/市/自治区名称是什么?
  [Unknown]:
该单位的双字母国家/地区代码是什么?
  [Unknown]:
CN=Unknown, OU=Unknown, O=Unknown, L=Unknown, ST=Unknown, C=Unknown是否正确?
  [否]:  y
```

参数说明：

- `genkey` - 表示要创建一个新的密钥。
- `alias` - 表示 keystore - 的别名。
- `keyalg` - 表示使用的加密算法是 - RSA - ，一种非对称加密算法。
- `keysize` - 表示密钥的长度。
- `keystore` - 表示生成的密钥存放位置。
- `validity` - 表示密钥的有效时间，单位为天。

命令执行后，会在当前目录生成一个名为 `keystore.p12` 的证书文件。

## 部署 HTTPS

（1）将 `keystore.p12` 证书文件置于 `src/main/resources` 目录下

（2）在 `application.properties` 中添加配置：

```properties
server.port = 8443
server.ssl.key-store = classpath:keystore.p12
server.ssl.key-alias = tomcat
server.ssl.key-store-password = 123456
```

其中：

- `key-store` - 表示密钥文件名。
- `key-alias` - 表示密钥别名。
- `key-store-password` - 就是在cmd命令执行过程中输入的密码。

配置完成后，就可以启动 Spring Boot 项目了，此时如果我们直接使用 Http 协议来访问接口，就会看到如下错误：

![img](https://raw.githubusercontent.com/dunwu/images/dev/snap/20200306101148.png)

改用 https 来访问 ，浏览器会提示：**您的链接不是私密连接**。这是因为使用的证书不被浏览器认可，不过没关系，继续访问即可。

## 请求转发

考虑到 Spring Boot 不支持同时启动 HTTP 和 HTTPS ，为了解决这个问题，我们这里可以配置一个请求转发，当用户发起 HTTP 调用时，自动转发到 HTTPS 上。

具体配置如下：

```java
@Configuration
public class TomcatConfig {
    @Bean
    TomcatServletWebServerFactory tomcatServletWebServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory(){
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint constraint = new SecurityConstraint();
                constraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                constraint.addCollection(collection);
                context.addConstraint(constraint);
            }
        };
        factory.addAdditionalTomcatConnectors(createTomcatConnector());
        return factory;
    }
    private Connector createTomcatConnector() {
        Connector connector = new
                Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(8000);
        connector.setSecure(false);
        connector.setRedirectPort(8443);
        return connector;
    }
}
```

在这里，我们配置了 Http 的请求端口为 8081，所有来自 8081 的请求，将被自动重定向到 8080 这个 https 的端口上。

如此之后，我们再去访问 http 请求，就会自动重定向到 https。

## 参考资料

- [Spring Boot 支持 Https 有那么难吗？](https://segmentfault.com/a/1190000020052375)
