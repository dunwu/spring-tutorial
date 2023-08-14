package example.spring.data.jdbc;

/**
 * 事务服务
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2023-01-22
 */
public interface UserDaoTxService {

    /**
     * 无事务示例
     */
    void noTransaction(User entity);

    /**
     * 声明式事务示例
     */
    void withTransaction(User entity);

    /**
     * 编程式事务示例
     */
    void withTransaction2(User entity);

}
