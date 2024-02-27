# Sentinel 示例说明

## 测试环境

### 启动 Sentinel 控制台

> 详见：[启动 Sentinel 控制台](https://sentinelguard.io/zh-cn/docs/dashboard.html)

（1）下载

从 [release 页面](https://github.com/alibaba/Sentinel/releases) 下载最新版本的控制台 jar 包

（2）启动

```shell
java -jar sentinel-dashboard2.jar -Dproject.name=sentinel-dashboard --server.port=18080
```

### 启动 Provider 服务

（1）执行 `example.spring.ratelimit.sentinel.SentinelProviderApplication` 类 main 方法

（2）启动时，设置 VM 参数：`-Dcsp.sentinel.dashboard.server=127.0.0.1:18080 -Dproject.name=provider`

### 启动 Consumer 服务

（1）执行 `example.spring.ratelimit.sentinel.SentinelConsumerApplication` 类 main 方法

（2）启动时，设置 VM 参数：`-Dcsp.sentinel.dashboard.server=127.0.0.1:18080 -Dproject.name=consumer`

## 抛出异常的方式定义资源示例

项目：provider

代码：example.spring.ratelimit.sentinel.RateLimitController

示例代码：

```java
    @GetMapping("/limit1")
    public String limit1() {
        // 1.5.0 版本开始可以利用 try-with-resources 特性
        // 资源名可使用任意有业务语义的字符串，比如方法名、接口名或其它可唯一标识的字符串。
        try (Entry entry = SphU.entry("limit1")) {
            // 被保护的业务逻辑
            log.info("limit1 -> 请求通过");
            return "ok";
        } catch (BlockException e) {
            // 资源访问阻止，被限流或被降级
            // 在此处进行相应的处理操作
            log.error("limit1 -> 请求限流", e);
            return "failed";
        }
    }
```

## 返回布尔值方式定义资源并限流示例

项目：provider

代码：example.spring.ratelimit.sentinel.RateLimitController

示例代码：

```java
    @GetMapping("/limit2")
    public String limit2() {
        // 资源名可使用任意有业务语义的字符串
        if (SphO.entry("limit2")) {
            // 务必保证finally会被执行
            try {
                // 被保护的业务逻辑
                log.info("limit2 -> 请求通过");
                return "ok";
            } finally {
                SphO.exit();
            }
        } else {
            // 资源访问阻止，被限流或被降级
            // 进行相应的处理操作
            log.error("limit2 -> 请求限流");
            return "failed";
        }
    }
```

## 注解方式定义资源

项目：provider

代码：example.spring.ratelimit.sentinel.RateLimitController

示例代码：

```java
    @GetMapping("/limit3")
    @SentinelResource(value = "limit3")
    public String limit3() {
        try {
            log.info("limit3 -> 请求通过");
            return "ok";
        } catch (Exception e) {
            log.error("limit3 -> 请求限流", e);
            return "failed";
        }
    }
```
