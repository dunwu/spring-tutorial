# spring-boot-web-helloworld

在本示例中，将构建一个具有静态主页，且可以接受 HTTP GET 请求的应用程序。此外，项目中展示了如果使用 JUnit5 测试 MVC。

## 运行应用程序

（1）执行 `example.spring.web.helloworld.WebHelloWorldApplication#main`，启动应用。

（2）当看到下图中相似内容，表明应用启动成功

![](https://raw.githubusercontent.com/dunwu/images/dev/snap/20221010201123.png)

此处打印的天气信息，来自于发起 HTTP 请求外部 API 站点。其相应代码如下：

```java
@Slf4j
@Service
public class WeatherService {

    private final RestTemplate restTemplate;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Weather getWeather(String cityCode) {
        return restTemplate.getForObject("http://t.weather.sojson.com/api/weather/city/" + cityCode, Weather.class);
    }

    public void printBasicWeatherInfo(Weather weather) {
        log.info("时间：{}", weather.getTime());
        log.info("空气质量：{}", weather.getData().getQuality());
        log.info("湿度：{}", weather.getData().getShidu());
        log.info("PM25数值：{}", weather.getData().getPm25());
        log.info("温度：{}", weather.getData().getWendu());
        log.info("建议：{}", weather.getData().getGanmao());
    }

}
```

（3）打开浏览器，访问 http://localhost:8080/，页面中会出现一行文本：**Get your greeting [here](http://localhost:8080/greeting)**。点击 here，跳转 http://localhost:8080/greeting，页面中会出现一行文本：Hello, World!

`/greeting` 接口对应的 HTTP 响应服务代码如下：

```java
@Controller
public class GreetingController {

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
        Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

}
```

## 测试应用程序

`spring-test` 包中提供了一个测试工具类 `MockMvc`，可以很方便的模拟 Spring MVC 请求，来测试 Spring MVC 接口。

一个基本的 Spring MVC 测试例如下：

```java
@SpringBootTest
@AutoConfigureMockMvc
public class WebMvcTests {

    @Autowired
    private MockMvc mockMvc;

    // ...
}
```

- `@SpringBootTest ` 注解告诉 Spring Boot 要寻找一个主配置类（例如，一个带有的 `@SpringBootApplication` 的类）并使用它来启动一个 Spring 应用程序上下文。
- `@AutoConfigureMockMvc` 注解告诉 Spring Boot 自动实例化一个 MockMvc，并为其构建所需的依赖。

本项目中提供了两个单元测试类，可以直接执行，查看输出结果

- `example.spring.web.helloworld.SendHttpMessageTests`
- `example.spring.web.helloworld.WebMvcTests`

## 参考资料

- https://spring.io/guides/gs/serving-web-content/
- https://spring.io/guides/gs/testing-web/
