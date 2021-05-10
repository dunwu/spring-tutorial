package io.github.dunwu.springboot.data.entities;

import io.github.dunwu.data.core.Pagination;
import io.github.dunwu.springboot.data.common.OrderType;
import io.github.dunwu.springboot.data.common.QueryJudgeType;
import io.github.dunwu.springboot.data.elasticsearch.QueryDocument;
import io.github.dunwu.springboot.data.elasticsearch.QueryField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-12-17
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@QueryDocument(orders = {
    @QueryDocument.Order(value = "age", type = OrderType.ASC),
    @QueryDocument.Order(value = "email", type = OrderType.DESC)
})
public class UserQuery extends Pagination<User> {

    @QueryField(judgeType = QueryJudgeType.Like)
    private String userName;

    @QueryField(judgeType = QueryJudgeType.Equals)
    private Integer age;

    @QueryField(judgeType = QueryJudgeType.Equals)
    private String email;

}
