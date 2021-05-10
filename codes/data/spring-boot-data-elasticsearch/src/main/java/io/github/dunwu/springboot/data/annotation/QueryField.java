package io.github.dunwu.springboot.data.annotation;

import io.github.dunwu.springboot.data.constant.QueryJudgeType;
import io.github.dunwu.springboot.data.constant.QueryLogicType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface QueryField {

    String value() default "";

    QueryLogicType logicType() default QueryLogicType.AND;

    QueryJudgeType judgeType() default QueryJudgeType.Equals;

}
