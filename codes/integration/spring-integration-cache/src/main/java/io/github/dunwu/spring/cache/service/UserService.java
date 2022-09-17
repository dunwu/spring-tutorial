package io.github.dunwu.spring.cache.service;

import io.github.dunwu.spring.cache.entity.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {

    private ConcurrentHashMap<Integer, User> map;

    public UserService() {
        // 模拟应用启动时加载缓存
        map = new ConcurrentHashMap<>();
        User user1 = new User(1, "张三");
        User user2 = new User(2, "赵四");
        User user3 = new User(3, "王五");
        map.put(user1.getId(), user1);
        map.put(user2.getId(), user2);
        map.put(user3.getId(), user3);
    }

    @Cacheable(value = { "users" }, key = "#user.id")
    public User findUser(User user) {
        return findUserInDb(user.getId());
    }

    /**
     * 模拟数据库查询操作
     */
    private User findUserInDb(int id) {
        User user = map.get(id);
        if (user != null) {
            System.out.println("查找数据库 id = " + id + " 成功");
            return user;
        }
        return null;
    }

    @Cacheable(value = "users", condition = "#user.getId() <= 2")
    public User findUserInLimit(User user) {
        return findUserInDb(user.getId());
    }

    @CachePut(value = "users", key = "#user.getId()")
    public void updateUser(User user) {
        updateUserInDb(user);
    }

    /**
     * 模拟数据库更新操作
     */
    private void updateUserInDb(User user) {
        User old = map.get(user.getId());
        if (old != null) {
            System.out.println("更新数据库" + old + " -> " + user);
            old.setName(user.getName());
        }
    }

    @CacheEvict(value = "users")
    public void removeUser(User user) {
        removeUserInDb(user.getId());
    }

    /**
     * 模拟数据库删除操作
     */
    private void removeUserInDb(int id) {
        map.remove(id);
        System.out.println("从数据库移除 id = " + id + " 的数据");
    }

    @CacheEvict(value = "users", allEntries = true)
    public void clear() {
        removeAllInDb();
    }

    private void removeAllInDb() {
        map.clear();
    }

}
