package com.nekonekod.tagger.taggerserver.db;

import com.nekonekod.tagger.taggerserver.annotation.WhereField;
import com.nekonekod.tagger.taggerserver.constant.QueryMatcher;
import com.nekonekod.tagger.taggerserver.constant.QueryOperator;
import com.nekonekod.tagger.taggerserver.util.ReflectUtil;
import com.nekonekod.tagger.taggerserver.util.StringUtil;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * build xpath query string
 *
 * @author duwenjun
 * @date 2018/1/4
 * @see WhereField
 */
@Log4j2
public class JxQueryUtil {

    private JxQueryUtil() {
    }

    public static <T> String buildJxQuery(Class<T> clazz, T arg, QueryOperator operator) {
        String delimiter = Optional.ofNullable(operator).orElse(QueryOperator.OR).getValue();
        Field[] fields = clazz.getDeclaredFields();
        String query = Arrays.stream(fields).map(field -> {
            WhereField annotation = field.getDeclaredAnnotation(WhereField.class);
            if (annotation == null) return null;
            String fName = annotation.fieldName().length == 0 ? field.getName() : annotation.fieldName()[0];
            Object value = ReflectUtil.getField(fName, arg);
            if (value == null) return null;
            return build(fName, value, annotation);
        }).filter(StringUtil::notNullOrEmpty).collect(Collectors.joining(delimiter));
        query = StringUtil.notNullOrEmpty(query) ? "/.[" + query + "]" : "/.";
        log.info("buildJxQuery:{}", query);
        return query;
    }

    private static String build(String field, Object value, WhereField annotation) {
        QueryMatcher matcher = annotation.matcher();
        if (annotation.listOperator().length > 0 && value instanceof Collection) {
            QueryOperator listOperator = annotation.listOperator()[0];
            //集合类型
            String q = ((Collection<?>) value).stream()
                    .distinct()
                    .map(v -> String.format(listTemplate(matcher, v), field, v))
                    .collect(Collectors.joining(listOperator.getValue()));
            return StringUtil.isNullOrEmpty(q) ? null : " (" + q + ") ";
        } else {
            return String.format(template(matcher, value), field, value);
        }
    }

    private static String template(QueryMatcher matcher, Object v) {
        String format = "";
        if (matcher == QueryMatcher.Equals) {
            format = !(v instanceof CharSequence) ? " %s=%d " : " %s='%s' ";
        } else if (matcher == QueryMatcher.Contains) {
            format = !(v instanceof CharSequence) ? " contains(%s,%d) " : " contains(%s,'%s') ";
        }
        return format;
    }

    private static String listTemplate(QueryMatcher matcher, Object v) {
        String format = "";
        if (matcher == QueryMatcher.Equals) {
            format = !(v instanceof CharSequence) ? " contains(@%s,%d) " : " contains(@%s,'%s' )";
        } else if (matcher == QueryMatcher.Contains) {
            format = !(v instanceof CharSequence) ? " ./%s[contains(.,%d)] " : " ./%s[contains(.,'%s')] ";
        }
        return format;
    }

}
