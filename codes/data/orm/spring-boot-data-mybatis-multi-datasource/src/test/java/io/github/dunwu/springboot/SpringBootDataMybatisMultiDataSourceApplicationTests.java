package io.github.dunwu.springboot;

import io.github.dunwu.springboot.data.entity.User;
import io.github.dunwu.springboot.data.mapper.Db1UserMapper;
import io.github.dunwu.springboot.data.mapper.Db2UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import javax.annotation.Resource;

@SpringBootTest(classes = { SpringBootDataMybatisMultiDataSourceApplication.class })
@ActiveProfiles({ "test" })
public class SpringBootDataMybatisMultiDataSourceApplicationTests {

    @Resource
    private Db1UserMapper db1UserMapper;

    @Resource
    private Db2UserMapper db2UserMapper;

    @BeforeEach
    public void before() {
        db1UserMapper.delete(null);
        db2UserMapper.delete(null);
    }

    @Test
    public void testDb1() {
        Long count = db1UserMapper.selectCount(null);
        Assertions.assertEquals(0, count.intValue());

        db1UserMapper.insert(new User("张三", 21, "南京", "xxx@163.com"));
        db1UserMapper.insert(new User("李四", 28, "上海", "xxx@163.com"));

        System.out.println(("----- selectAll method test ------"));
        List<User> userList = db1UserMapper.selectList(null);
        Assertions.assertEquals(2, userList.size());
        userList.forEach(System.out::println);
    }

    @Test
    public void testDb2() {
        Long count = db2UserMapper.selectCount(null);
        Assertions.assertEquals(0, count.intValue());

        db2UserMapper.insert(new User("王五", 24, "北京", "xxx@163.com"));

        System.out.println(("----- selectAll method test ------"));
        List<User> userList = db2UserMapper.selectList(null);
        Assertions.assertEquals(1, userList.size());
        userList.forEach(System.out::println);
    }

}
