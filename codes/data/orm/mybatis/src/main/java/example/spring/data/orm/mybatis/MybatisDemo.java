package example.spring.data.orm.mybatis;

import example.spring.data.orm.mybatis.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

/**
 * Mybatis 手动加载示例
 *
 * @author peng.zhang
 * @date 2021/5/10
 */
public class MybatisDemo {

    public static void main(String[] args) throws Exception {
        // 1. 加载 mybatis 配置文件，创建 SqlSessionFactory
        // 注：在实际的应用中，SqlSessionFactory 应该是单例
        InputStream inputStream = Resources.getResourceAsStream("mybatis/mybatis-config.xml");
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(inputStream);

        // 2. 创建一个 SqlSession 实例，进行数据库操作
        SqlSession sqlSession = factory.openSession();

        // 3. 使用SqlSession查询
        Long params = 1L;
        List<User> list =
            sqlSession.selectList("example.spring.data.orm.mybatis.mapper.UserMapper.selectByPrimaryKey", params);
        for (User user : list) {
            System.out.println("user name: " + user.getName());
        }
        // 输出：user name: 张三
    }

}
