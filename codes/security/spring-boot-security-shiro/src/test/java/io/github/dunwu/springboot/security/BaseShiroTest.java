package io.github.dunwu.springboot.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * Shiro 基本认证、授权测试例
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-17
 */
@DisplayName("Shiro 基本认证、授权测试例")
public class BaseShiroTest {

    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @BeforeEach
    public void addUser() {
        // 添加一个测试账户
        simpleAccountRealm.addAccount("root", "root", "admin", "user");
    }

    @Test
    @DisplayName("基本认证测试例")
    public void testAuthentication() {

        // 构建 SecurityManager
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);

        // Subject 提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager); // 设置 SecurityManager
        Subject subject = SecurityUtils.getSubject(); // 获取当前 Subject

        // 登录
        UsernamePasswordToken token = new UsernamePasswordToken("root", "root");
        subject.login(token);

        // subject.isAuthenticated() 用于判断用户是否认证成功
        System.out.println("isAuthenticated:" + subject.isAuthenticated());
        Assertions.assertTrue(subject.isAuthenticated());

        // 登出
        subject.logout();

        System.out.println("isAuthenticated:" + subject.isAuthenticated());
        Assertions.assertFalse(subject.isAuthenticated());
    }

    @Test
    @DisplayName("基本授权测试例")
    public void testAuthorization() {

        // 构建 SecurityManager
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);

        // Subject 提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager); // 设置 SecurityManager
        Subject subject = SecurityUtils.getSubject(); // 获取当前 Subject

        // 登录
        UsernamePasswordToken token = new UsernamePasswordToken("root", "root");
        subject.login(token);

        // subject.isAuthenticated() 用于判断用户是否认证成功
        System.out.println("isAuthenticated:" + subject.isAuthenticated());
        Assertions.assertTrue(subject.isAuthenticated());

        // 判断 subject 是否具有 admin 和 user 两个角色权限，如没有则会报错
        subject.checkRoles("admin", "user");
        Assertions.assertTrue(subject.hasRole("admin"));
        Assertions.assertTrue(subject.hasRole("user"));
        Assertions.assertFalse(subject.hasRole("xxx"));

        Assertions.assertTrue(subject.hasAllRoles(Arrays.asList("admin", "user")));
        Assertions.assertFalse(subject.hasAllRoles(Arrays.asList("admin", "user", "xxx")));
    }

}
