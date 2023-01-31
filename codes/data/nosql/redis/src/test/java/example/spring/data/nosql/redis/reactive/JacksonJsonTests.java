/*
 * Copyright 2017-2018 the original author or authors.
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

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.util.ByteUtils;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.nio.ByteBuffer;

/**
 * Show usage of reactive Template API on Redis lists using {@link ReactiveRedisOperations} with Jackson serialization.
 *
 * @author Mark Paluch
 */
@Slf4j
@ActiveProfiles(value = { "single" })
@SpringBootTest(classes = RedisReactiveConfiguration.class)
public class JacksonJsonTests {

    @Autowired
    ReactiveRedisOperations<String, Person> typedOperations;

    @Autowired
    ReactiveRedisOperations<String, Object> genericOperations;

    /**
     * {@link ReactiveRedisOperations} using {@link String} keys and {@link Person} values serialized via
     * {@link org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer} to JSON without additional type
     * hints.
     *
     * @see RedisReactiveConfiguration#reactiveJsonPersonRedisTemplate(ReactiveRedisConnectionFactory)
     */
    @Test
    public void shouldWriteAndReadPerson() {

        StepVerifier.create(typedOperations.opsForValue().set("homer", new Person("Homer", "Simpson"))) //
                    .expectNext(true) //
                    .verifyComplete();

        Flux<String> get =
            typedOperations.execute(conn -> conn.stringCommands().get(ByteBuffer.wrap("homer".getBytes()))) //
                           .map(ByteUtils::getBytes) //
                           .map(String::new);

        StepVerifier.create(get) //
                    .expectNext("{\"firstname\":\"Homer\",\"lastname\":\"Simpson\"}") //
                    .verifyComplete();

        StepVerifier.create(typedOperations.opsForValue().get("homer")) //
                    .expectNext(new Person("Homer", "Simpson")) //
                    .verifyComplete();
    }

    /**
     * {@link ReactiveRedisOperations} using {@link String} keys and {@link Object} values serialized via
     * {@link org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer} to JSON with additional type
     * hints. This example uses the non-final type {@link Person} using its FQCN as type identifier.
     *
     * @see RedisReactiveConfiguration#reactiveJsonObjectRedisTemplate(ReactiveRedisConnectionFactory)
     */
    @Test
    public void shouldWriteAndReadPersonObject() {

        StepVerifier.create(genericOperations.opsForValue().set("homer", new Person("Homer", "Simpson"))) //
                    .expectNext(true) //
                    .verifyComplete();

        Flux<String> get =
            genericOperations.execute(conn -> conn.stringCommands().get(ByteBuffer.wrap("homer".getBytes()))) //
                             .map(ByteUtils::getBytes) //
                             .map(String::new);

        StepVerifier.create(get) //
                    .expectNext(
                        "{\"_type\":\"example.spring.data.nosql.redis.reactive.Person\",\"firstname\":\"Homer\",\"lastname\":\"Simpson\"}") //
                    .verifyComplete();

        StepVerifier.create(genericOperations.opsForValue().get("homer")) //
                    .expectNext(new Person("Homer", "Simpson")) //
                    .verifyComplete();
    }

    /**
     * {@link ReactiveRedisOperations} using {@link String} keys and {@link Object} values serialized via
     * {@link org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer} to JSON with additional type
     * hints. This example uses the final type {@link EmailAddress} using configuration from
     * {@link com.fasterxml.jackson.annotation.JsonTypeInfo} as type identifier.
     *
     * @see RedisReactiveConfiguration#reactiveJsonObjectRedisTemplate(ReactiveRedisConnectionFactory)
     */
    @Test
    public void shouldWriteAndReadEmailObject() {

        StepVerifier.create(genericOperations.opsForValue().set("mail", new EmailAddress("homer@the-simpsons.com"))) //
                    .expectNext(true) //
                    .verifyComplete();

        Flux<String> get =
            genericOperations.execute(conn -> conn.stringCommands().get(ByteBuffer.wrap("mail".getBytes()))) //
                             .map(ByteUtils::getBytes) //
                             .map(String::new);

        StepVerifier.create(get) //
                    .expectNext(
                        "{\"_type\":\"example.spring.data.nosql.redis.reactive.EmailAddress\",\"address\":\"homer@the-simpsons.com\"}") //
                    .verifyComplete();

        StepVerifier.create(genericOperations.opsForValue().get("mail")) //
                    .expectNext(new EmailAddress("homer@the-simpsons.com")) //
                    .verifyComplete();
    }

}
