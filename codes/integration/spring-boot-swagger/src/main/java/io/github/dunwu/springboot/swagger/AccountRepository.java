package io.github.dunwu.springboot.swagger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * JPA Rest 接口，对应 user 表
 * <p>
 * 启动 Application 后，直接访问：http://<host:ip>/user
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-10-12
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * 根据用户名查找用户
     * <p>
     * 示例：http://localhost:8080/user/search/findByName?name=lisi
     *
     * @param username 用户名
     * @return {@link Account}
     */
    Account findByUsername(@Param("username") String username);

    /**
     * 根据邮箱查找用户
     *
     * @param email 邮箱
     * @return {@link Account}
     */
    @Query("from Account u where u.email=:email")
    Account findByEmail(@Param("email") String email);

    /**
     * 根据用户名删除用户
     *
     * @param username 用户名
     */
    void deleteByUsername(@Param("username") String username);

}
