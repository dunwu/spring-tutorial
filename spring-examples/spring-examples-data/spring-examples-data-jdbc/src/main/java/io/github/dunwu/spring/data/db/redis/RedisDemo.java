/**
 * The Apache License 2.0
 * Copyright (c) 2016 Zhang Peng
 */
package io.github.dunwu.spring.data.db.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

/**
 * Jedis 操作Redis的简单示例
 *
 * @author Zhang Peng
 * @date 2017/4/12.
 */
public class RedisDemo {
    private static final Logger logger = LoggerFactory.getLogger(RedisDemo.class);

    public static void main(String[] args) {
        // 连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost", 6379);
        // 认证密码
        jedis.auth("root");

        // redis ping命令
        logger.info("redis ping 结果：{}", jedis.ping());

        // redis string 类型读写
        jedis.set("name", "亚里士多德");
        logger.info("redis中存储了string，name={}", jedis.get("name"));

        // redis list 类型读写
        //存储数据到列表中
        jedis.lpush("mylist", "how");
        jedis.lpush("mylist", "are");
        jedis.lpush("mylist", "you");
        // 获取存储的数据并输出
        List<String> list = jedis.lrange("mylist", 0 ,5);
        String result = "redis中存储了list\n";
        for(String item : list) {
            result += item + "\n";
        }
        logger.info(result);

        // 获取数据并输出
        Set<String> keys = jedis.keys("*");
        result = "读取redis中所有的key：\n";
        for(String item : keys) {
            result += item + "\n";
        }
        logger.info(result);
    }
}
