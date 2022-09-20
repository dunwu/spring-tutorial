package io.github.dunwu.springboot.data.entities;

import io.github.dunwu.springboot.data.annotation.QueryDocument;
import io.github.dunwu.springboot.data.annotation.QueryField;
import io.github.dunwu.springboot.data.constant.OrderType;
import io.github.dunwu.springboot.data.constant.QueryJudgeType;
import io.github.dunwu.tool.data.Pagination;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-12-17
 */
@Data
@ToString
@QueryDocument(orders = {
    @QueryDocument.Order(value = "age", type = OrderType.ASC),
    @QueryDocument.Order(value = "email", type = OrderType.DESC)
})
public class UserQuery extends Pagination<User> {

    @Id
    private String id;

    @QueryField(judgeType = QueryJudgeType.Like)
    private String username;

    @QueryField(judgeType = QueryJudgeType.Equals)
    private Integer age;

    @QueryField(judgeType = QueryJudgeType.Equals)
    private String email;

}
