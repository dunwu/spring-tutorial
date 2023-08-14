package example.spring.data.nosql.mongodb;

import cn.hutool.core.collection.CollectionUtil;
import example.spring.data.nosql.mongodb.entity.Role;
import example.spring.data.nosql.mongodb.entity.User;
import example.spring.data.nosql.mongodb.repository.RoleRepository;
import example.spring.data.nosql.mongodb.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2023-01-29
 */
@SpringBootTest
public class CompoundRepositoryTests {

    public static final long INIT_NUM = 1000L;
    public static final LocalDateTime minDate = LocalDateTime.of(1990, 1, 1, 0, 0, 0);
    public static final LocalDateTime maxDate = LocalDateTime.of(2010, 1, 1, 0, 0, 0);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    private Role admin;
    private Role user;
    private Role visitors;

    private User dunwu;

    private List<Role> roles;

    @BeforeEach
    public void init() {
        initRoles();
        initUsers();
    }

    private void initRoles() {
        roleRepository.deleteAll();

        visitors = Role.builder().code("visitors").name("访客").level(1).build();
        user = Role.builder().code("user").name("普通用户").level(2).build();
        admin = Role.builder().code("admin").name("管理员").level(99).build();
        roles = new ArrayList<>();
        roles.add(visitors);
        roles.add(user);
        roles.add(admin);
        roleRepository.saveAll(roles);
    }

    private void initUsers() {

        userRepository.deleteAll();

        List<User> users = new ArrayList<>();
        for (long i = 1L; i <= INIT_NUM; i++) {
            User user = User.builder()
                            .userId(i)
                            .username(RandomUtil.randomChineseName())
                            .birthday(RandomUtil.randomDate(minDate, maxDate))
                            .mobile("151xxxxxxxx")
                            .email(RandomUtil.randomEmail())
                            .disabled(RandomUtil.randomBoolean())
                            .roles(RandomUtil.randomEleList(roles, 2))
                            .build();
            users.add(user);
        }

        dunwu = User.builder()
                    .userId(INIT_NUM + 1)
                    .username("钝悟")
                    .birthday(RandomUtil.randomDate(minDate, maxDate))
                    .mobile("15122223333")
                    .email("dunwu@xxx.com")
                    .disabled(false)
                    .roles(Collections.singletonList(admin))
                    .build();
        userRepository.save(dunwu);

        userRepository.saveAll(users);
    }

    @Nested
    @DisplayName("Repository 查询方式测试")
    class RepositoryQuery {

        @Test
        @DisplayName("测试 count 方法")
        public void count() {
            long count = userRepository.count();
            System.out.println("用户记录数：" + count);
            Assertions.assertThat(count).isEqualTo(INIT_NUM + 1);
        }

        @Test
        @DisplayName("测试 findAll 方法")
        public void findAll() {
            Iterable<User> users = userRepository.findAll(Sort.by(Sort.Direction.ASC, "birthday"));
            users.forEach(System.out::println);
        }

        @Test
        @DisplayName("测试 findById 方法")
        public void findById() {
            Iterable<User> users = userRepository.findAll(Sort.by(Sort.Direction.ASC, "birthday"));
            users.forEach(System.out::println);

            User user = users.iterator().next();

            userRepository.findById(user.getId()).ifPresent(result -> {
                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result.getId()).isEqualTo(user.getId());
            });
        }

        @Test
        @DisplayName("测试 findXXXByXXX 方法")
        public void findXXXByXXX() {
            User result = userRepository.findUserByUserId(INIT_NUM + 1);
            Assertions.assertThat(result).isNotNull();
            Assertions.assertThat(result.getUserId()).isEqualTo(dunwu.getUserId());

            List<User> list = userRepository.findUsersByUsername("钝悟");
            Assertions.assertThat(list).isNotEmpty();
            Assertions.assertThat(list.get(0).getUserId()).isEqualTo(dunwu.getUserId());
        }

        @Test
        @DisplayName("测试 findPagedXXXByXXX 方法")
        public void findPagedXXXByXXX() {
            List<User> list = userRepository.findPagedUsersByUsernameLike("张",
                PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "userId")));
            list.forEach(System.out::println);
            Assertions.assertThat(list).isNotEmpty();
        }

        @Test
        @DisplayName("测试 delete 方法")
        public void delete() {
            long count = userRepository.count();
            Assertions.assertThat(count).isEqualTo(INIT_NUM + 1);

            User user = userRepository.findUserByUserId(INIT_NUM + 1);
            userRepository.deleteById(user.getId());

            long count2 = userRepository.count();
            Assertions.assertThat(count2).isEqualTo(INIT_NUM);
        }

    }

    @Nested
    @DisplayName("Criteria 查询方式测试")
    class CriteriaQuery {

        public static final int PAGE_SIZE = 50;

        @Test
        @DisplayName("测试 Criteria 查询")
        public void testCriteria() {

            Criteria criteria = Criteria.where("disabled").is(false);
            Query query = Query.query(criteria);

            long count = 0L;
            long total = mongoTemplate.count(query, "user");
            long num = total / PAGE_SIZE + (total % PAGE_SIZE == 0 ? 0 : 1);
            for (int i = 0; i < num; i++) {
                PageRequest pageRequest = PageRequest.of(i, PAGE_SIZE, Sort.by(Sort.Direction.ASC, "userId"));
                List<User> list = mongoTemplate.find(Query.query(criteria).with(pageRequest), User.class, "user");
                if (CollectionUtil.isEmpty(list)) {
                    break;
                }
                count += list.size();
                System.out.printf("第 %s 页数据：\n", i);
                list.forEach(System.out::println);
            }
            Assertions.assertThat(count).isEqualTo(total);
        }

        @Test
        @DisplayName("测试 Example 查询")
        public void testExample() {

            User user = User.builder().disabled(false).build();
            Example<User> example = Example.of(user);
            Criteria criteria = Criteria.byExample(example);
            Query query = Query.query(criteria);

            long count = 0L;
            long total = mongoTemplate.count(query, User.class, "user");
            long num = total / PAGE_SIZE + (total % PAGE_SIZE == 0 ? 0 : 1);
            for (int i = 0; i < num; i++) {
                PageRequest pageRequest = PageRequest.of(i, PAGE_SIZE, Sort.by(Sort.Direction.ASC, "userId"));
                List<User> list = mongoTemplate.find(Query.query(criteria).with(pageRequest), User.class, "user");
                if (CollectionUtil.isEmpty(list)) {
                    break;
                }
                count += list.size();
                System.out.printf("第 %s 页数据：\n", i);
                list.forEach(System.out::println);
            }
            Assertions.assertThat(count).isEqualTo(total);
        }

    }

}
