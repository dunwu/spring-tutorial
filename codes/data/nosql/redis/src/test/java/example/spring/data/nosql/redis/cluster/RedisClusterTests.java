/*
 * Copyright 2015-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example.spring.data.nosql.redis.cluster;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;

/**
 * {@link RedisClusterTests} shows general usage of {@link RedisTemplate} and {@link RedisOperations} in a clustered
 * environment.
 *
 * @author Christoph Strobl
 */
@DisplayName("Redis 集群服务（Cluster）访问测试")
@ActiveProfiles(value = { "cluster" })
@SpringBootTest(classes = { RedisClusterApplication.class })
public class RedisClusterTests {

    @Autowired
    RedisTemplate<String, String> template;

    @BeforeEach
    public void setUp() {
        template.execute((RedisCallback<String>) connection -> {
            connection.flushDb();
            return "OK";
        });
    }

    /**
     * Operation executed on a single node and slot. <br /> -&gt; {@code SLOT 5798} served by {@code 127.0.0.1:30002}
     */
    @Test
    public void singleSlotOperation() {
        template.opsForValue().set("spring:tutorial:name", "rand al'thor"); // slot 5798
        Assertions.assertThat(template.opsForValue().get("spring:tutorial:name")).isEqualTo("rand al'thor");
    }

    /**
     * Operation executed on multiple nodes and slots. <br /> -&gt; {@code SLOT 5798} served by {@code 127.0.0.1:30002}
     * <br /> -&gt; {@code SLOT 14594} served by {@code 127.0.0.1:30003}
     */
    @Test
    public void multiSlotOperation() {

        template.opsForValue().set("spring:tutorial:name", "matrim cauthon"); // slot 5798
        template.opsForValue().set("spring:tutorial:nickname", "prince of the ravens"); // slot 14594
        Assertions.assertThat(
                      template.opsForValue().multiGet(Arrays.asList("spring:tutorial:name", "spring:tutorial:nickname")))
                  .contains("matrim cauthon", "prince of the ravens");
    }

    /**
     * Operation executed on a single node and slot because of pinned slot key <br /> -&gt; {@code SLOT 5798} served by
     * {@code 127.0.0.1:30002}
     */
    @Test
    public void fixedSlotOperation() {

        template.opsForValue().set("spring:tutorial:{user}:name", "perrin aybara"); // slot 5474
        template.opsForValue().set("spring:tutorial:{user}:nickname", "wolfbrother"); // slot 5474

        Assertions.assertThat(template.opsForValue()
                                      .multiGet(Arrays.asList("spring:tutorial:{user}:name",
                                          "spring:tutorial:{user}:nickname")))
                  .contains("perrin aybara", "wolfbrother");
    }

    /**
     * Operation executed across the cluster to retrieve cumulated result. <br /> -&gt; {@code KEY age} served by
     * {@code 127.0.0.1:30001} <br /> -&gt; {@code KEY name} served by {@code 127.0.0.1:30002} <br /> -&gt;
     * {@code KEY nickname} served by {@code 127.0.0.1:30003}
     */
    @Test
    public void multiNodeOperation() {

        template.opsForValue().set("spring:tutorial:name", "rand al'thor"); // slot 5798
        template.opsForValue().set("spring:tutorial:nickname", "dragon reborn"); // slot 14594
        template.opsForValue().set("spring:tutorial:age", "23"); // slot 741;
        Assertions.assertThat(template.keys("*"))
                  .contains("spring:tutorial:name", "spring:tutorial:nickname", "spring:tutorial:age");
    }

}
