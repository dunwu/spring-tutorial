package io.github.dunwu.spring.core.bean.define.factory;

import io.github.dunwu.spring.core.bean.entity.person.User;

/**
 * {@link User} 工厂类
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since
 */
public interface UserFactory {

    default User createUser() {
        return User.createUser();
    }

}
