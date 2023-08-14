package example.spring.data.orm.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("`t_user`")
public class User {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer age;
    private String address;
    private String email;

    public User() { }

    public User(String name, Integer age, String address, String email) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.email = email;
    }

}
