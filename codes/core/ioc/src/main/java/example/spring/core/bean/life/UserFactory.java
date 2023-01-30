package example.spring.core.bean.life;

import example.spring.core.bean.entity.person.User;

/**
 * {@link User} 工厂类
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 */
public interface UserFactory {

    default User createUser() {
        return User.createUser();
    }

}
