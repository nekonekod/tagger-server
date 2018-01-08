package com.nekonekod.tagger.taggerserver.annotation;

import com.nekonekod.tagger.taggerserver.constant.QueryMatcher;
import com.nekonekod.tagger.taggerserver.constant.QueryOperator;
import com.nekonekod.tagger.taggerserver.constant.QueryType;
import com.nekonekod.tagger.taggerserver.db.JxQueryUtil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * annotation for building query
 *
 * @author duwenjun
 * @date 2018/1/4
 * @see com.nekonekod.tagger.taggerserver.db.SQLiteHelper
 * @see JxQueryUtil
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JxQuery {

    String[] fieldName() default {};

    QueryType type() default QueryType.String;

    QueryMatcher matcher();

    QueryOperator[] listOperator() default {};

}
