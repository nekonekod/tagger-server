package com.nekonekod.tagger.taggerserver.annotation;

import com.nekonekod.tagger.taggerserver.constant.JxQueryMatcher;
import com.nekonekod.tagger.taggerserver.constant.JxQueryOperator;
import com.nekonekod.tagger.taggerserver.constant.JxQueryType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author duwenjun
 * @date 2018/1/4
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JxQuery {

    JxQueryType type() default JxQueryType.String;

    JxQueryMatcher matcher();

    JxQueryOperator listOperator() default JxQueryOperator.NULL;

}
