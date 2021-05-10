package io.github.dunwu.springboot.data.util;

import io.github.dunwu.springboot.data.annotation.QueryDocument;
import io.github.dunwu.springboot.data.annotation.QueryField;
import io.github.dunwu.springboot.data.constant.QueryJudgeType;
import io.github.dunwu.springboot.data.constant.QueryLogicType;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.RegexpQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.lang.reflect.Field;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-12-17
 */
public class QueryFeildToQueryBuilderUtil {

    public static final String LIKE_REGEX_TEMPLATE = ".*%s.*";

    public static SearchQuery transQueryDto2Condition(final Object queryBean) throws IllegalAccessException {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();

        QueryDocument document = queryBean.getClass().getAnnotation(QueryDocument.class);
        if (null == document) {
            throw new IllegalArgumentException("查询条件类定义必须使用 @QueryDocument 注解");
        }

        // 排序条件
        if (StringUtils.isNotBlank(document.orderItem())) {
            SortOrder order = SortOrder.fromString(document.orderType().name());
            FieldSortBuilder fieldSortBuilder = new FieldSortBuilder(document.orderItem()).order(order);
            nativeSearchQueryBuilder.withSort(fieldSortBuilder);
        }

        Field[] fields = queryBean.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(queryBean);

            if (value != null && StringUtils.isNotBlank(value.toString())) {
                QueryField queryField = field.getAnnotation(QueryField.class);
                if (null == queryField) {
                    continue;
                }

                QueryLogicType logicType = queryField.logicType();
                QueryJudgeType judgeType = queryField.judgeType();

                String fieldName;
                if (StringUtils.isNotBlank(queryField.value())) {
                    fieldName = queryField.value();
                } else {
                    fieldName = field.getName();
                }

                if (StringUtils.isBlank(fieldName)) {
                    continue;
                }

                QueryBuilder queryBuilder = null;
                switch (judgeType) {
                    case Equals:
                        queryBuilder = new TermQueryBuilder(fieldName, value);
                        break;
                    case Like:
                        String regexp = String.format(LIKE_REGEX_TEMPLATE, value);
                        queryBuilder = new RegexpQueryBuilder(fieldName, regexp);
                        break;
                    default:
                        break;
                }
                nativeSearchQueryBuilder.withQuery(queryBuilder);
            }
        }

        return nativeSearchQueryBuilder.build();
    }

}
