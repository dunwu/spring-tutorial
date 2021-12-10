package io.github.dunwu.spring.core.bean.define.factory;

import io.github.dunwu.spring.core.bean.entity.person.User;
import org.springframework.beans.factory.FactoryBean;

/**
 * {@link User} Bean 的 {@link FactoryBean} 实现
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since
 */
public class UserFactoryBean implements FactoryBean<User> {

    @Override
    public User getObject() throws Exception {
        return User.createUser();
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }

}
