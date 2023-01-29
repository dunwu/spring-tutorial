package example.spring.core.ioc.inject;

import example.spring.core.bean.entity.person.User;

/**
 * {@link User} 的 Holder 类
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 */
public class UserHolder {

    private User user;

    public UserHolder() { }

    public UserHolder(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserHolder{" +
            "user=" + user +
            '}';
    }

}
