package com.nekonekod.tagger.taggerserver.util;

import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;

/**
 * @author duwenjun
 * @date 2017/12/28
 * @copyright sankuai.com
 */
public class StringUtil {

    /**
     * abc_abc -> abcAbc
     *
     * @param str
     * @return
     */
    public static String lowerUnderscoreToLowerCamel(String str) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, str);
    }

    /**
     * abcAbc -> abc_abc
     *
     * @param str
     * @return
     */
    public static String lowerCamelToLowerUnderscore(String str) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, str);
    }

    /**
     * ABC_ABC -> AbcAbc
     *
     * @param str
     * @return
     */
    public static String upperUnderscoreToUpperCamel(String str) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, str);
    }

    /**
     * AbcAbc -> ABC_ABC
     *
     * @param str
     * @return
     */
    public static String upperCamelToUpperUnderscore(String str) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, str);
    }

    /**
     * 拼接字符串
     *
     * @param separator
     * @param strs
     * @return
     */
    public static String join(String separator, Object... strs) {
        return Joiner.on(separator).skipNulls().join(strs);
    }

    /**
     * 拼接字符串
     *
     * @param separator
     * @param iter
     * @return
     */
    public static String join(String separator, Iterable<?> iter) {
        return Joiner.on(separator).skipNulls().join(iter);
    }

    public static boolean isNullOrEmpty(String str) {
        return Strings.isNullOrEmpty(str);
    }

    public static String emptyToNull(String str) {
        return Strings.emptyToNull(str);
    }

    public static String nullToEmpty(String str) {
        return Strings.nullToEmpty(str);
    }

    public static String padStart(String str, int minLength, char padChar) {
        return Strings.padStart(str, minLength, padChar);
    }

    public static String padEnd(String str, int minLength, char padChar) {
        return Strings.padEnd(str, minLength, padChar);
    }

}
