package example.spring.data.orm.mybatis.dao;

import example.spring.data.orm.mybatis.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

/**
 * @author Zhang Peng
 * @since 2017/4/13.
 */
@ExtendWith(value = { SpringExtension.class })
@ContextConfiguration(locations = { "classpath:mybatis/mybatis-spring.xml" })
public class UserDaoTest {

    @Autowired
    private UserDAO userDAO;

    @Test
    public void testInsert() {
        User user = new User();
        user.setName("赵四");
        user.setAge(13);
        user.setEmail("xxx@xxx.com");
        user.setAddress("南京");
        try {
            userDAO.insert(user);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Test
    public void testSelect() {
        List<User> users = userDAO.selectAll();
        for (User user : users) {
            System.out.println("user name: " + user.getName());
        }
    }

}
