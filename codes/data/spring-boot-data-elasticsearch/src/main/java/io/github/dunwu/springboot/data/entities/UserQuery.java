package io.github.dunwu.springboot.data.entities;

import io.github.dunwu.springboot.data.annotation.QueryDocument;
import io.github.dunwu.springboot.data.annotation.QueryField;
import io.github.dunwu.springboot.data.constant.OrderType;
import io.github.dunwu.springboot.data.constant.QueryJudgeType;
import io.github.dunwu.springboot.data.constant.QueryLogicType;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-12-17
 */
@Data
@ToString
@QueryDocument(value = User.class, orderType = OrderType.DESC, orderItem = "age")
public class UserQuery {

    @Id
    private String id;

    @QueryField(logicType = QueryLogicType.OR, judgeType = QueryJudgeType.Like)
    private String username;

    @QueryField(logicType = QueryLogicType.AND, judgeType = QueryJudgeType.Equals)
    private Integer age;

    @QueryField(logicType = QueryLogicType.OR, judgeType = QueryJudgeType.Equals)
    private String email;

}
