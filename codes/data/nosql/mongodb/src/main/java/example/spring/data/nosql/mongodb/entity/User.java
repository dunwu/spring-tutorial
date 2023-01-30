package example.spring.data.nosql.mongodb.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * 用户信息实体（MongoDB）
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2023-01-29
 */
@Data
@Builder
@Document(collection = "user")
@TypeAlias(value = "userInfo")
@CompoundIndexes(value = {
    @CompoundIndex(name = "idx_userid_disabled", def = "{'userId':1, 'disabled':false}")
})
public class User {

    /**
     * ID
     */
    @Id
    private String id;

    /**
     * 用户 ID
     */
    @Indexed(unique = true)
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 是否禁用
     */
    private Boolean disabled;

    /**
     * 角色列表
     */
    private List<Role> roles;

    @Override
    public String toString() {
        return "User{" +
            "id='" + id + '\'' +
            ", userId='" + userId + '\'' +
            ", username='" + username + '\'' +
            ", birthday=" + birthday +
            ", mobile='" + mobile + '\'' +
            ", email='" + email + '\'' +
            '}';
    }

}
