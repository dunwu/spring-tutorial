package example.spring.data.jdbc;

/**
 * 事务服务
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2023-01-22
 */
public interface UserDaoTxService {

    void noTransaction();

    void withTransaction();

}
