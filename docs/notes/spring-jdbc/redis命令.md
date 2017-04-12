# redis命令概述

Redis 命令用于在 redis 服务上执行操作。

要在 redis 服务上执行命令需要一个 redis 客户端。Redis 客户端在我们之前下载的的 redis 的安装包中。

启动 redis 客户端，打开终端并输入命令 **redis-cli**。该命令会连接本地的 redis 服务。

```shell
$redis-cli
redis 127.0.0.1:6379>
redis 127.0.0.1:6379> PING

PONG
```

## 在远程服务上执行命令

如果需要在远程 redis 服务上执行命令，同样我们使用的也是 **redis-cli** 命令。

**语法**

```
$ redis-cli -h host -p port -a password
```

**实例**

以下实例演示了如何连接到主机为 127.0.0.1，端口为 6379 ，密码为 mypass 的 redis 服务上。

```shell
$redis-cli -h 127.0.0.1 -p 6379 -a "mypass"
redis 127.0.0.1:6379>
redis 127.0.0.1:6379> PING

PONG
```

# 常用命令

## 帮助命令

我始终认为：任何应用的命令组中，最重要、也是最先需要了解的就是帮助命令。毕竟，记住所有命令的用法是比较困难的。所以，需要经常通过help命令查看各种命令的用法。

### 查看分组命令

redis的命令可以按以下类别分组：

- cluster
- connection
- geo
- generic
- hashe
- hyperloglog
- keys
- list
- pubsub
- scripting
- server
- set
- sorted_set
- string
- transactions

使用`help`命令可以查看各分组下的可使用命令，语法为：

```shell
help @<group>
```

例-查看transactions(事务相关)的命令

```shell
127.0.0.1:6379> help @transactions

  DISCARD -
  summary: Discard all commands issued after MULTI
  since: 2.0.0

  EXEC -
  summary: Execute all commands issued after MULTI
  since: 1.2.0

  MULTI -
  summary: Mark the start of a transaction block
  since: 1.2.0

  UNWATCH -
  summary: Forget about all watched keys
  since: 2.2.0

  WATCH key [key ...]
  summary: Watch the given keys to determine execution of the MULTI/EXEC block
  since: 2.2.0
```

### 查看具体命令

查看具体命令的语法：

```shell
help <command>
```

例 - 查看ping命令的用法

```shell
127.0.0.1:6379> help ping

  PING [message]
  summary: Ping the server
  since: 1.0.0
  group: connection
```

# 参考资料

[runoob redis教程](http://www.runoob.com/redis/redis-tutorial.html)