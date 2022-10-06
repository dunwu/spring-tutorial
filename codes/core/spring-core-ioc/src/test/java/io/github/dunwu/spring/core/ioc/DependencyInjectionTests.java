package io.github.dunwu.spring.core.ioc;

import cn.hutool.json.JSONUtil;
import io.github.dunwu.spring.core.bean.entity.job.Musician;
import io.github.dunwu.spring.core.bean.entity.job.Poet;
import io.github.dunwu.spring.core.bean.entity.person.City;
import io.github.dunwu.spring.core.bean.inject.UserHolder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DisplayName("测试依赖注入")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(value = { "/META-INF/ioc/DependencyInject.xml" })
public class DependencyInjectionTests {

    @Autowired
    @Qualifier("personA")
    private Person personA;

    @Autowired
    @Qualifier("personB")
    private Person personB;

    @Autowired
    @Qualifier("personC")
    private Person personC;

    @Autowired
    private Poet poet;

    @Autowired
    private Musician musician;

    @Autowired
    private UserHolder userByConstructor;

    @Autowired
    private UserHolder userBySetter;

    @Autowired
    private UserHolder userByConstructorAutowiring;

    @Autowired
    private UserHolder userBySetterAutowiring;

    @Test
    @DisplayName("测试依赖注入")
    public void testDependencyInjection() {
        // 测试构造注入
        System.out.println(JSONUtil.toJsonStr(userByConstructor.getUser()));
        Assertions.assertThat(userByConstructor.getUser().getId()).isEqualTo(1);
        Assertions.assertThat(userByConstructor.getUser().getName()).isEqualTo("钝悟");
        Assertions.assertThat(userByConstructor.getUser().getCity()).isEqualTo(City.SHANGHAI);

        // 测试 Setter 注入
        System.out.println(JSONUtil.toJsonStr(userBySetter.getUser()));
        Assertions.assertThat(userBySetter.getUser().getId()).isEqualTo(1);
        Assertions.assertThat(userBySetter.getUser().getName()).isEqualTo("钝悟");
        Assertions.assertThat(userBySetter.getUser().getCity()).isEqualTo(City.SHANGHAI);

        // 测试构造注入自动装配
        System.out.println(JSONUtil.toJsonStr(userByConstructorAutowiring.getUser()));
        Assertions.assertThat(userByConstructorAutowiring.getUser().getId()).isEqualTo(1);
        Assertions.assertThat(userByConstructorAutowiring.getUser().getName()).isEqualTo("钝悟");
        Assertions.assertThat(userByConstructorAutowiring.getUser().getCity()).isEqualTo(City.SHANGHAI);

        // 测试 Setter 注入自动装配
        System.out.println(JSONUtil.toJsonStr(userBySetterAutowiring.getUser()));
        Assertions.assertThat(userBySetterAutowiring.getUser().getId()).isEqualTo(1);
        Assertions.assertThat(userBySetterAutowiring.getUser().getName()).isEqualTo("钝悟");
        Assertions.assertThat(userBySetterAutowiring.getUser().getCity()).isEqualTo(City.SHANGHAI);
    }

    @Test
    @DisplayName("构造参数匹配测试1")
    public void testConstructor01() {
        Assertions.assertThat(JSONUtil.toJsonStr(personA))
                  .isEqualTo("{\"name\":\"张三\",\"age\":18}");
        Assertions.assertThat(JSONUtil.toJsonStr(personB))
                  .isEqualTo("{\"name\":\"李四\",\"age\":19}");
    }

    @Test
    @DisplayName("构造参数匹配测试2")
    public void testConstructor02() {
        Assertions.assertThat(JSONUtil.toJsonStr(personC))
                  .isEqualTo("{\"name\":\"王五\",\"age\":25}");
    }

    @Test
    @DisplayName("构造注入")
    public void testConstructorDI() {
        Assertions.assertThat(JSONUtil.toJsonStr(poet))
                  .isEqualTo("{\"name\":\"李白\",\"poetry\":{\"name\":\"将进酒\"}}");
    }

    @Test
    @DisplayName("Setter 注入")
    public void testSetterDI() {
        Assertions.assertThat(musician.getName()).isEqualTo("肖邦");
        Assertions.assertThat(musician.getSong()).isEqualTo("夜曲");
        Assertions.assertThat(musician.getInstrument().play()).isEqualTo("演奏钢琴");
    }

}
