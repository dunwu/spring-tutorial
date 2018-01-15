package io.github.dunwu.spring.security.shiro;

import javax.sql.DataSource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.github.dunwu.spring.security.shiro.entity.Permission;
import io.github.dunwu.spring.security.shiro.entity.Role;
import io.github.dunwu.spring.security.shiro.entity.User;
import io.github.dunwu.spring.security.shiro.realm.UserRealm;
import io.github.dunwu.spring.security.shiro.service.PermissionService;
import io.github.dunwu.spring.security.shiro.service.RoleService;
import io.github.dunwu.spring.security.shiro.service.UserService;

/**
 * <p>
 * User: Zhang Kaitao
 * <p>
 * Date: 14-2-12
 * <p>
 * Version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-beans.xml",
                "classpath:spring/spring-shiro.xml"})
public class ShiroTest {
    private static final Logger log = LoggerFactory.getLogger(ShiroTest.class);

    @Autowired
    protected PermissionService permissionService;
    @Autowired
    protected RoleService roleService;
    @Autowired
    protected UserService userService;
    @Autowired
    private UserRealm userRealm;

    protected JdbcTemplate jdbcTemplate;

    @Autowired
    private void setDataSource(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    protected String password = "123";
    protected Permission p1;
    protected Permission p2;
    protected Permission p3;
    protected Role r1;
    protected Role r2;
    protected User u1;
    protected User u2;
    protected User u3;
    protected User u4;

    @Before
    public void setUp() {
        // 0、清空表数据
        jdbcTemplate.update("delete from sys_users");
        jdbcTemplate.update("delete from sys_roles");
        jdbcTemplate.update("delete from sys_permissions");
        jdbcTemplate.update("delete from sys_users_roles");
        jdbcTemplate.update("delete from sys_roles_permissions");

        // 1、新增权限
        p1 = new Permission("user:create", "用户模块新增", Boolean.TRUE);
        p2 = new Permission("user:update", "用户模块修改", Boolean.TRUE);
        p3 = new Permission("menu:create", "菜单模块新增", Boolean.TRUE);
        permissionService.createPermission(p1);
        permissionService.createPermission(p2);
        permissionService.createPermission(p3);

        // 2、新增角色
        r1 = new Role("admin", "管理员", Boolean.TRUE);
        r2 = new Role("user", "用户管理员", Boolean.TRUE);
        roleService.createRole(r1);
        roleService.createRole(r2);

        // 3、关联角色-权限
        roleService.correlationPermissions(r1.getId(), p1.getId());
        roleService.correlationPermissions(r1.getId(), p2.getId());
        roleService.correlationPermissions(r1.getId(), p3.getId());
        roleService.correlationPermissions(r2.getId(), p1.getId());
        roleService.correlationPermissions(r2.getId(), p2.getId());

        // 4、新增用户
        u1 = new User("zhangsan", password);
        u2 = new User("lisi", password);
        u3 = new User("wangwu", password);
        u4 = new User("zhaoliu", password);
        u4.setLocked(Boolean.TRUE);
        userService.createUser(u1);
        userService.createUser(u2);
        userService.createUser(u3);
        userService.createUser(u4);

        // 5、关联用户-角色
        userService.correlationRoles(u1.getId(), r1.getId());
    }

    @Test
    public void testLoginSuccess() {
        login(u1.getUsername(), password);
    }

    /**
     * 用错误的用户名、密码登录
     */
    @Test
    public void testLoginFail() {
        String changePassword = "123456";
        login(u1.getUsername(), changePassword);
    }

    /**
     * 更新DB中的用户名、密码，并使用新数据登录
     */
    @Test
    public void testLoginChanged() {
        String changePassword = "123456";
        userService.changePassword(u1.getId(), changePassword);
        log.debug("更改 {} 的密码为 {}", u1.getUsername(), changePassword);
        login(u1.getUsername(), changePassword);
    }

    private void login(String username, String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
            subject.checkRole("admin");
            subject.checkPermission("user:create");
            log.debug("username: {}, password: {}，登录成功!", username, password);
        } catch (Exception e) {
            log.debug("username: {}, password: {}; 登录失败!", username, password);
        } finally {
            userRealm.clearAllCache();
            if (subject.isAuthenticated()) {
                subject.logout();
            }
        }
    }
}
