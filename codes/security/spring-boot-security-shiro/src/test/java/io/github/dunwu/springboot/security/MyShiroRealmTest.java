package io.github.dunwu.springboot.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * 实现 Realm，使得 Shiro 可以访问应用自身数据源测试例
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-17
 */
@DisplayName("实现 Realm，使得 Shiro 可以访问应用自身数据源测试例")
public class MyShiroRealmTest {

    MyRealm myRealm = new MyRealm();

    @Test
    public void testAuthentication() {

        // 构建 SecurityManager
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(myRealm);

        // Subject 提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager); // 设置 SecurityManager
        Subject subject = SecurityUtils.getSubject(); // 获取当前 Subject

        // 登录
        UsernamePasswordToken token = new UsernamePasswordToken("root", "root");
        subject.login(token);

        // subject.isAuthenticated() 用于判断用户是否认证成功
        System.out.println("isAuthenticated:" + subject.isAuthenticated());
        Assertions.assertTrue(subject.isAuthenticated());

        // 判断 subject 是否是指定的一个或多个角色
        subject.checkRoles("admin", "user");
        Assertions.assertTrue(subject.hasRole("admin"));
        Assertions.assertTrue(subject.hasRole("user"));
        Assertions.assertFalse(subject.hasRole("xxx"));
        Assertions.assertTrue(subject.hasAllRoles(Arrays.asList("admin", "user")));
        Assertions.assertFalse(subject.hasAllRoles(Arrays.asList("admin", "user", "xxx")));

        // 判断 subject 是否是拥有指定的一个或多个权限
        subject.checkPermission("user:add");
        subject.checkPermission("user:delete");
        subject.checkPermissions("user:add", "user:delete");
        Assertions.assertTrue(subject.isPermitted("user:add"));
        Assertions.assertTrue(subject.isPermitted("user:delete"));
        Assertions.assertTrue(subject.isPermittedAll("user:add", "user:delete"));
        Assertions.assertFalse(subject.isPermittedAll("user:add", "user:delete", "user:update"));
    }

    static class MyRealm extends AuthorizingRealm {

        /**
         * 模拟数据库存储
         */
        private final Map<String, String> userMap = new HashMap<>();

        public MyRealm() {
            super.setName("myRealm");
            userMap.put("root", "root");
            userMap.put("user", "user");
        }

        @Override
        protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
            String userName = (String) principals.getPrimaryPrincipal();

            // 模拟从数据库中查询角色
            Set<String> roles = getRolesByUserName(userName);
            // 模拟从数据库中查询权限
            Set<String> permissions = getPermissionsByUserName(userName);

            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            simpleAuthorizationInfo.setStringPermissions(permissions);
            simpleAuthorizationInfo.setRoles(roles);
            return simpleAuthorizationInfo;
        }

        @Override
        protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
            // 1.获取用户 Principal 和 Credentials
            String username = (String) token.getPrincipal();
            String password = new String((char[]) token.getCredentials());

            // 模拟从数据库中查找用户
            if (!userMap.containsKey(username)) {
                throw new UnknownAccountException("账号不存在");
            }

            // 模拟从数据库中查找用户对应的密码
            if (!userMap.get(username).equals(password)) {
                throw new IncorrectCredentialsException("密码错误");
            }

            return new SimpleAuthenticationInfo(username, password, "admin");
        }

        /**
         * 模拟从数据库中获取权限数据
         *
         * @param userName 用户名
         * @return 权限集合
         */
        private Set<String> getPermissionsByUserName(String userName) {
            Set<String> permissions = new HashSet<>();
            permissions.add("user:delete");
            permissions.add("user:add");
            return permissions;
        }

        /**
         * 模拟从数据库中获取角色数据
         *
         * @param userName 用户名
         * @return 角色集合
         */
        private Set<String> getRolesByUserName(String userName) {
            Set<String> roles = new HashSet<>();
            roles.add("admin");
            roles.add("user");
            return roles;
        }

        /**
         * 模拟从数据库取凭证的过程
         *
         * @param userName 用户名
         * @return 密码
         */
        private String getPasswordByUserName(String userName) {
            return userMap.get(userName);
        }

    }

}
