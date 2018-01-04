package com.nekonekod.tagger.taggerserver.util;

import com.nekonekod.tagger.taggerserver.annotation.JxQuery;
import com.nekonekod.tagger.taggerserver.constant.JxQueryMatcher;
import com.nekonekod.tagger.taggerserver.constant.JxQueryOperator;
import com.nekonekod.tagger.taggerserver.constant.JxQueryType;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author duwenjun
 * @date 2018/1/4
 */
@Log4j2
public class JxQueryUtil {

    private JxQueryUtil() {
    }

    public static <T> String buildJxQuery(Class<T> clazz, T arg, JxQueryOperator operator) {
        String delimiter = Optional.ofNullable(operator).orElse(JxQueryOperator.OR).getValue();
        Field[] fields = clazz.getDeclaredFields();
        String query = Arrays.stream(fields).map(field -> {
            JxQuery annotation = field.getDeclaredAnnotation(JxQuery.class);
            if (annotation == null) return null;
            Object value = ReflectUtil.getField(field.getName(), arg);
            if (value == null) return null;
            return build(field.getName(), value, annotation);
        }).filter(StringUtil::notNullOrEmpty).collect(Collectors.joining(delimiter));
        return StringUtil.notNullOrEmpty(query) ? "/.[" + query + "]" : "/.";
    }

    private static String build(String field, Object value, JxQuery annotation) {
        JxQueryType type = annotation.type();
        JxQueryMatcher matcher = annotation.matcher();
        JxQueryOperator listOperator = annotation.listOperator();
        if (value instanceof Collection) {
            //集合类型
            String q = ((Collection<?>) value).stream()
                    .distinct()
                    .map(v -> String.format(listTemplate(matcher, type), field, v))
                    .collect(Collectors.joining(listOperator.getValue()));
            return StringUtil.isNullOrEmpty(q) ? null : " (" + q + ") ";
        } else {
            return String.format(template(matcher, type), field, value);
        }
    }

    private static String template(JxQueryMatcher matcher, JxQueryType type) {
        String format = "";
        if (matcher == JxQueryMatcher.Equals) {
            format = type == JxQueryType.Number ? " %s=%d " : " %s='%s' ";
        } else if (matcher == JxQueryMatcher.Contains) {
            format = type == JxQueryType.Number ? " contains(%s,%d) " : " contains(%s,'%s') ";
        }
        return format;
    }

    private static String listTemplate(JxQueryMatcher matcher, JxQueryType type) {
        String format = "";
        if (matcher == JxQueryMatcher.Equals) {
            format = type == JxQueryType.Number ? " contains(@%s,%d) " : " contains(@%s,'%s' )";
        } else if (matcher == JxQueryMatcher.Contains) {
            format = type == JxQueryType.Number ? " ./%s[contains(.,%d)] " : " ./%s[contains(.,'%s')] ";
        }
        return format;
    }
}
