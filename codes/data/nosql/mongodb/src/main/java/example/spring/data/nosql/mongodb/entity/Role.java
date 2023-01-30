package example.spring.data.nosql.mongodb.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 用户信息实体（MongoDB）
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2023-01-29
 */
@Data
@Builder
@Document(collection = "role")
@TypeAlias(value = "roleInfo")
@CompoundIndexes(value = {
    @CompoundIndex(name = "idx_code_disabled", def = "{'code':1, 'disabled':false}")
})
public class Role {

    /**
     * ID
     */
    @Id
    private String id;

    /**
     * 角色编码
     */
    @Indexed(unique = true)
    private String code;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色级别
     */
    private Integer level;

    /**
     * 是否禁用
     */
    private Boolean disabled;

}
