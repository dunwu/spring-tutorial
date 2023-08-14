package example.spring.data.orm.jpa;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Spring Boot + JPA 基本 CRUD 测试
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-10-12
 */
@Slf4j
@SpringBootTest(classes = { DataJpaApplication.class })
public class DataJpaTests {

    @Autowired
    private UserRepository repository;

    @BeforeEach
    public void before() {
        repository.deleteAll();
    }

    @Test
    public void insert() {
        User user = new User("张三", 18, "北京", "user1@163.com");
        repository.save(user);
        Optional<User> optional = repository.findById(user.getId());
        Assertions.assertThat(optional).isNotNull();
        assertThat(optional.isPresent()).isTrue();
    }

    @Test
    public void batchInsert() {
        List<User> users = new ArrayList<>();
        users.add(new User("张三", 18, "北京", "user1@163.com"));
        users.add(new User("李四", 19, "上海", "user2@163.com"));
        users.add(new User("王五", 18, "南京", "user3@163.com"));
        users.add(new User("赵六", 20, "武汉", "user4@163.com"));
        repository.saveAll(users);

        long count = repository.count();
        assertThat(count).isEqualTo(4);

        List<User> list = repository.findAll();
        Assertions.assertThat(list).isNotEmpty().hasSize(4);
        list.forEach(this::accept);
    }

    private void accept(User user) { log.info(user.toString()); }

    @Test
    public void delete() {
        List<User> users = new ArrayList<>();
        users.add(new User("张三", 18, "北京", "user1@163.com"));
        users.add(new User("李四", 19, "上海", "user2@163.com"));
        users.add(new User("王五", 18, "南京", "user3@163.com"));
        users.add(new User("赵六", 20, "武汉", "user4@163.com"));
        repository.saveAll(users);

        repository.deleteByName("张三");
        assertThat(repository.findByName("张三")).isNull();

        repository.deleteAll();
        List<User> list = repository.findAll();
        Assertions.assertThat(list).isEmpty();
    }

    @Test
    public void findAllInPage() {
        List<User> users = new ArrayList<>();
        users.add(new User("张三", 18, "北京", "user1@163.com"));
        users.add(new User("李四", 19, "上海", "user2@163.com"));
        users.add(new User("王五", 18, "南京", "user3@163.com"));
        users.add(new User("赵六", 20, "武汉", "user4@163.com"));
        repository.saveAll(users);

        PageRequest pageRequest = PageRequest.of(1, 2);
        Page<User> page = repository.findAll(pageRequest);
        Assertions.assertThat(page).isNotNull();
        assertThat(page.isEmpty()).isFalse();
        assertThat(page.getTotalElements()).isEqualTo(4);
        assertThat(page.getTotalPages()).isEqualTo(2);

        List<User> list = page.get().collect(Collectors.toList());
        System.out.println("user list: ");
        list.forEach(System.out::println);
    }

    @Test
    public void update() {
        User oldUser = new User("张三", 18, "北京", "user1@163.com");
        oldUser.setName("张三丰");
        repository.save(oldUser);

        User newUser = repository.findByName("张三丰");
        assertThat(newUser).isNotNull();
    }

}
