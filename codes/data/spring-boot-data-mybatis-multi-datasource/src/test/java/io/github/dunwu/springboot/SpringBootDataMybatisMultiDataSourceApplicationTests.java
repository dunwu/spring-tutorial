package io.github.dunwu.springboot;

import io.github.dunwu.springboot.data.entity.User;
import io.github.dunwu.springboot.data.mapper.Db1UserMapper;
import io.github.dunwu.springboot.data.mapper.Db2UserMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import javax.annotation.Resource;

@SpringBootTest
@ActiveProfiles({ "test" })
@RunWith(SpringRunner.class)
public class SpringBootDataMybatisMultiDataSourceApplicationTests {

    @Resource
    private Db1UserMapper db1UserMapper;

    @Resource
    private Db2UserMapper db2UserMapper;

    @Before
    public void before() {
        db1UserMapper.delete(null);
        db2UserMapper.delete(null);
    }

    @Test
    public void testDb1() {
        Integer count = db1UserMapper.selectCount(null);
        Assert.assertEquals(0, count.intValue());

        db1UserMapper.insert(new User("张三", 21, "南京", "xxx@163.com"));
        db1UserMapper.insert(new User("李四", 28, "上海", "xxx@163.com"));

        System.out.println(("----- selectAll method test ------"));
        List<User> userList = db1UserMapper.selectList(null);
        Assert.assertEquals(2, userList.size());
        userList.forEach(System.out::println);
    }

    @Test
    public void testDb2() {
        Integer count = db2UserMapper.selectCount(null);
        Assert.assertEquals(0, count.intValue());

        db2UserMapper.insert(new User("王五", 24, "北京", "xxx@163.com"));

        System.out.println(("----- selectAll method test ------"));
        List<User> userList = db2UserMapper.selectList(null);
        Assert.assertEquals(1, userList.size());
        userList.forEach(System.out::println);
    }

}
