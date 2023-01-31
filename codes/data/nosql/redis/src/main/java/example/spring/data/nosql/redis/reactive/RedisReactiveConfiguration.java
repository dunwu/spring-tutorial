/*
 * Copyright 2016-2018 the original author or authors.
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
package example.spring.data.nosql.redis.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializationContext.RedisSerializationContextBuilder;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author Mark Paluch
 */
@SpringBootApplication
public class RedisReactiveConfiguration {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    /**
     * Configures a {@link ReactiveRedisTemplate} with {@link String} keys and
     * {@link GenericJackson2JsonRedisSerializer}.
     */
    @Bean
    public ReactiveRedisTemplate<String, Object> reactiveJsonObjectRedisTemplate(
        ReactiveRedisConnectionFactory connectionFactory) {

        RedisSerializationContextBuilder<String, Object> builder = RedisSerializationContext
            .newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, Object> serializationContext = builder
            .value(new GenericJackson2JsonRedisSerializer("_type")).build();

        return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
    }

    /**
     * Configures a {@link ReactiveRedisTemplate} with {@link String} keys and a typed
     * {@link Jackson2JsonRedisSerializer}.
     */
    @Bean
    public ReactiveRedisTemplate<String, Person> reactiveJsonPersonRedisTemplate(
        ReactiveRedisConnectionFactory connectionFactory) {

        Jackson2JsonRedisSerializer<Person> serializer = new Jackson2JsonRedisSerializer<>(Person.class);
        RedisSerializationContextBuilder<String, Person> builder = RedisSerializationContext
            .newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, Person> serializationContext = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
    }

    @Bean
    public ReactiveRedisOperations<String, Coffee> redisOperations(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<Coffee> serializer = new Jackson2JsonRedisSerializer<>(Coffee.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, Coffee> builder =
            RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, Coffee> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }

    public static void main(String[] args) {
        SpringApplication.run(RedisReactiveConfiguration.class, args);
    }

}
