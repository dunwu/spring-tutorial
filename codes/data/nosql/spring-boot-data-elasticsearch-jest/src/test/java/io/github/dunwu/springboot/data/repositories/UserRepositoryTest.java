package io.github.dunwu.springboot.data.repositories;

import io.github.dunwu.springboot.SpringBootDataElasticsearchApplication;
import io.github.dunwu.springboot.data.constant.QueryLogicType;
import io.github.dunwu.springboot.data.elasticsearch.ElasticSearchUtil;
import io.github.dunwu.springboot.data.entities.User;
import io.github.dunwu.springboot.data.entities.UserQuery;
import io.github.dunwu.tool.util.RandomUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SpringBootDataElasticsearchApplication.class })
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    // @Before
    // public void clear() {
    // 	userRepository.deleteAll();
    //
    // 	userRepository.save(new User("刘备", 32, "xxxxxx", "forbreak@163.com"));
    // 	userRepository.save(new User("关羽", 31, "xxxxxx", "xxxxxxx@163.com"));
    // 	userRepository.save(new User("张飞", 29, "xxxxxx", "123xxx@163.com"));
    // 	userRepository.save(new User("赵云", 26, "xxxxxx", "x23xxxx@163.com"));
    // }

    @Test
    public void testCount() {
        long count = userRepository.count();
        assertThat(count).isEqualTo(4L);
    }

    @Test
    public void testSave() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            User user = new User(RandomUtil.randomChineseName(),
                RandomUtil.randomInt(18, 99), RandomUtil.randomString(6, 10),
                RandomUtil.randomEmail());
            users.add(user);
        }
        userRepository.saveAll(users);
        long count = userRepository.count();
        assertThat(count).isEqualTo(1004L);
    }

    @Test
    public void testFind() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            User user = new User(RandomUtil.randomChineseName(), RandomUtil.randomInt(18, 99),
                RandomUtil.randomString(6, 10), RandomUtil.randomEmail());
            users.add(user);
        }
        userRepository.saveAll(users);

        Iterable<User> userList = userRepository.findAll();
        userList.forEach(System.out::println);

        System.out.println("User found with findByUsername(\"张鹏\"):");
        System.out.println("--------------------------------");
        List<User> userList2 = userRepository.findByUserName("鹏");
        assertThat(userList2).isNotEmpty();
        userList2.forEach(System.out::println);

        System.out.println("Users found with findByEmail(\"forbreak@163.com\"):");
        System.out.println("--------------------------------");
        User user = userRepository.findByEmail("forbreak@163.com");
        System.out.println(user);
        assertThat(user).isNotNull();
    }

    @Test
    public void searchTest() throws IllegalAccessException {
        // List<User> users = new ArrayList<>();
        // for (int i = 0; i < 10000; i++) {
        // 	User user = new User(String.valueOf(i), RandomUtil.randomChineseName(), RandomUtil.randomInt(18, 99),
        // 		RandomUtil.randomString(6, 10), RandomUtil.randomEmail());
        // 	users.add(user);
        // }
        // userRepository.saveAll(users);

        UserQuery userQuery = new UserQuery();
        userQuery.setUserName("张");
        userQuery.setAge(20);

        List<User> list = ElasticSearchUtil.search(userRepository, userQuery, QueryLogicType.OR);
        System.out.println("Total Match: " + list.size());
        list.forEach(System.out::println);
    }

    @Test
    public void pageSearchTest() throws IllegalAccessException, NoSuchFieldException {
        // List<User> users = new ArrayList<>();
        // for (int i = 0; i < 10000; i++) {
        // 	User user = new User(String.valueOf(i), RandomUtil.randomChineseName(), RandomUtil.randomInt(18, 99),
        // 		RandomUtil.randomString(6, 10), RandomUtil.randomEmail());
        // 	users.add(user);
        // }
        // userRepository.saveAll(users);

        UserQuery userQuery = new UserQuery();
        userQuery.setUserName("张");
        userQuery.setAge(20);

        // userQuery.setSize(20);
        // userQuery.setCurrent(2);

        Page<User> page = ElasticSearchUtil.pageSearch(userRepository, userQuery, QueryLogicType.OR);
        // String info = String.format("total:%s, page:%s, size:%s", page.getTotalElements(), userQuery.getCurrent(),
        //     userQuery.getSize());
        // System.out.println(info);
        page.getContent().forEach(System.out::println);
    }

    @Test
    public void testUpdate() {
        User user = userRepository.findByEmail("forbreak@163.com");
        System.out.println("Before Update: " + user);
        assertThat(user).isNotNull();

        user.setEmail("modify@modify.com");
        userRepository.deleteById(user.getId());
        userRepository.save(user);
        Optional<User> optional = userRepository.findById(user.getId());
        assertThat(optional).isNotNull();
        assertThat(optional.get().getEmail()).isEqualTo("modify@modify.com");
        assertThat(optional.get().getId()).isEqualTo(user.getId());
        System.out.println("After Update: " + optional.get());
    }

}
