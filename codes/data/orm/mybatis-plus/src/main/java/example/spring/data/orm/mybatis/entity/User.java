package example.spring.data.orm.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户实体对应表 user
 * </p>
 *
 * @author hubin
 * @since 2018-08-11
 */
@Data
@Accessors(chain = true)
@TableName("`t_user`")
public class User {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer age;
    private String address;
    private String email;
    @TableField(exist = false)
    private String ignoreColumn = "ignoreColumn";
    @TableField(exist = false)
    private Integer count;

}
