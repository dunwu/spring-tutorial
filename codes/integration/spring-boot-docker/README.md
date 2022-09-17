# SpringBootTutorial :: Docker

> 本例展示一个 Spring Boot 项目如何通过 Docker 构建部署。
>
> [Spring Boot with Docker](https://spring.io/guides/gs/spring-boot-docker/)
>
> 通过 [dockerfile-maven](https://github.com/spotify/dockerfile-maven) 实现。

## 默认部署

> 先完成一个简单的 Spring Boot Web 项目

（1）引入依赖

引入 Spring Boot Web 必要的依赖和插件。

```xml
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>2.1.1.RELEASE</version>
</parent>

<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<build>
  <plugins>
    <plugin>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-maven-plugin</artifactId>
    </plugin>
  </plugins>
</build>
```

（2）添加一个启动类

```java
@SpringBootApplication
@RestController
public class DockerApplication {

    @RequestMapping("/")
    public String home() {
        return "Hello Docker World";
    }

    public static void main(String[] args) {
        SpringApplication.run(DockerApplication.class, args);
    }

}
```

（3）构建并启动

执行命令

```
mvn clean package && java -jar target/spring-boot-docker-0.0.1.jar
```

## Docker 部署

（1）添加插件

```xml
 <properties>
  <docker.image.prefix>dunwu</docker.image.prefix>
</properties>

<plugin>
  <groupId>com.spotify</groupId>
  <artifactId>dockerfile-maven-plugin</artifactId>
  <version>1.3.6</version>
  <executions>
    <execution>
      <id>default</id>
      <phase>install</phase>
      <goals>
        <goal>build</goal>
        <goal>push</goal>
      </goals>
    </execution>
  </executions>
  <configuration>
    <repository>${docker.image.prefix}/${project.artifactId}</repository>
    <tag>${project.version}</tag>
    <buildArgs>
      <JAR_FILE>${project.build.finalName}.jar</JAR_FILE>
    </buildArgs>
  </configuration>
</plugin>
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-dependency-plugin</artifactId>
  <executions>
    <execution>
      <id>unpack</id>
      <phase>package</phase>
      <goals>
        <goal>unpack</goal>
      </goals>
      <configuration>
        <artifactItems>
          <artifactItem>
            <groupId>${project.groupId}</groupId>
            <artifactId>${project.artifactId}</artifactId>
            <version>${project.version}</version>
          </artifactItem>
        </artifactItems>
      </configuration>
    </execution>
  </executions>
</plugin>
```

（2）定义 Dockerfile

```dockerfile
FROM openjdk:8-jdk-alpine
MAINTAINER zhangpeng <forbreak@163.com>
VOLUME /tmp
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","io.github.dunwu.springboot.SpringBootDockerApplication"]
```

（3）docker 构建

执行 `mvn clean package -DskipTests=true dockerfile:build`

执行成功后，就会在本地产生镜像，执行 `docker images` 就可以看到构建的镜像文件。

（4）启动镜像

执行 `docker run -e "SPRING_PROFILES_ACTIVE=dev" -p 8080:8080 -t dunwu/spring-boot-docker:1.0.0` 即可启动 docker 镜像。
