package io.github.dunwu.springboot.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

/**
 * 用户实体，对应 user 表
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-11-18
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    private String username;

    private String password;

    private String email;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof User)) {
            return false;
        }

        User user = (User) o;

        if (id != null && id.equals(user.id)) {
            return true;
        }

        return username.equals(user.username);
    }

}
