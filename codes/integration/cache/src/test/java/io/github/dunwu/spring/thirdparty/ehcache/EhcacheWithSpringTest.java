package io.github.dunwu.spring.thirdparty.ehcache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.github.dunwu.spring.thirdparty.ehcache.service.UserService;
import io.github.dunwu.spring.thirdparty.ehcache.vo.User;

/**
 * 本类展示并测试 Spring + Ehcache 的缓存解决方案
 *
 * @author Zhang Peng
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
public class EhcacheWithSpringTest {
    @Autowired
    UserService userService;

    /**
     * 测试@Cacheable
     */
    @Test
    public void testFindUser() throws InterruptedException {
        // 设置查询条件
        User user1 = new User(1, null);
        User user2 = new User(2, null);
        User user3 = new User(3, null);
        User user4 = new User(3, null);

        System.out.println("第一次查询");
        System.out.println(userService.findUser(user1));
        System.out.println(userService.findUser(user2));
        System.out.println(userService.findUser(user3));
        System.out.println(userService.findUser(user4));

        System.out.println("\n第二次查询");
        System.out.println(userService.findUser(user1));
        System.out.println(userService.findUser(user2));
        System.out.println(userService.findUser(user3));
        System.out.println(userService.findUser(user4));

        // 在classpath:ehcache/ehcache.xml中，设置了userCache的缓存时间为3000 ms, 这里设置等待
        Thread.sleep(3000);

        System.out.println("\n缓存过期，再次查询");
        System.out.println(userService.findUser(user1));
        System.out.println(userService.findUser(user2));
        System.out.println(userService.findUser(user3));
    }

    /**
     * 测试@Cacheable设置Spring SpEL条件限制
     */
    @Test
    public void testFindUserInLimit() throws InterruptedException {
        // 设置查询条件
        User user1 = new User(1, null);
        User user2 = new User(2, null);
        User user3 = new User(3, null);

        System.out.println("第一次查询user info");
        System.out.println(userService.findUserInLimit(user1));
        System.out.println(userService.findUserInLimit(user2));
        System.out.println(userService.findUserInLimit(user3));

        System.out.println("\n第二次查询user info");
        System.out.println(userService.findUserInLimit(user1));
        System.out.println(userService.findUserInLimit(user2));
        System.out.println(userService.findUserInLimit(user3)); // 超过限制条件，不会从缓存中读数据

        // 在classpath:ehcache/ehcache.xml中，设置了userCache的缓存时间为3000 ms, 这里设置等待
        Thread.sleep(3000);

        System.out.println("\n缓存过期，再次查询");
        System.out.println(userService.findUserInLimit(user1));
        System.out.println(userService.findUserInLimit(user2));
        System.out.println(userService.findUserInLimit(user3));
    }

    /**
     * 测试@CachePut
     */
    @Test
    public void testUpdateUser() {
        // 设置查询条件
        User user1 = new User(2, null);
        User user2 = new User(2, null);

        System.out.println(userService.findUser(user1));
        System.out.println(userService.findUser(user2));
        userService.updateUser(new User(2, "尼古拉斯.赵四"));
        System.out.println(userService.findUser(user1));
        System.out.println(userService.findUser(user2));
    }

    /**
     * 测试@CacheEvict删除指定缓存
     */
    @Test
    public void testRemoveUser() {
        // 设置查询条件
        User user1 = new User(1, null);

        System.out.println("数据删除前：");
        System.out.println(userService.findUser(user1));

        userService.removeUser(user1);
        System.out.println("数据删除后：");
        System.out.println(userService.findUser(user1));
    }

    /**
     * 测试@CacheEvict删除所有缓存
     */
    @Test
    public void testClear() {
        System.out.println("数据清空前：");
        System.out.println(userService.findUser(new User(1, null)));
        System.out.println(userService.findUser(new User(2, null)));
        System.out.println(userService.findUser(new User(3, null)));

        userService.clear();
        System.out.println("\n数据清空后：");
        System.out.println(userService.findUser(new User(1, null)));
        System.out.println(userService.findUser(new User(2, null)));
        System.out.println(userService.findUser(new User(3, null)));
    }
}
