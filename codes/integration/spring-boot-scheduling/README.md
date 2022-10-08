# SpringBootTutorial :: Schedule

## 开启时间调度

使用 `@EnableScheduling` 注解开启时间调度服务。

```java
@SpringBootApplication
@EnableScheduling
public class ScheduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleApplication.class, args);
    }

}
```

使用 `@Scheduled` 注解修饰方法，其中的 cron 表达式为。

```java
@Component
public class ScheduledTask {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");

    @Scheduled(cron = "0/10 * * * * ? ")
    public void cron() {
        log.info("[cron]The time is now {}", dateFormat.format(new Date()));
    }

    @Scheduled(fixedRate = 5000)
    public void fixedRate() {
        log.info("[fixedRate]The time is now {}", dateFormat.format(new Date()));
    }

    @Scheduled(fixedDelay = 5000)
    public void fixedDelay() {
        log.info("[fixedDelay]The time is now {}", dateFormat.format(new Date()));
    }

    @Scheduled(initialDelay = 1000, fixedRate = 5000)
    public void initialDelay() {
        log.info("[initialDelay]The time is now {}", dateFormat.format(new Date()));
    }
}
```
