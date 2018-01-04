package com.nekonekod.tagger.taggerserver.util;

import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author duwenjun
 * @date 2017/12/28
 * @copyright sankuai.com
 */
public class StringUtil {

    public static final Pattern SEQ_FORMAT = Pattern.compile("\\{}");

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

    public static boolean notNullOrEmpty(String str) {
        return !Strings.isNullOrEmpty(str);
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

    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String trimToNull(String str) {
        if (isNullOrEmpty(str)) return null;
        String trim = str.trim();
        return isNullOrEmpty(trim) ? null : trim;
    }

    /**
     * "NiHao","World" -> NiHao
     * "","Hello" -> Hello
     * null,"Hello" -> Hello
     * @param str
     * @param def
     * @return
     */
    public static String notBlankElseDefault(String str, String def) {
        return notNullOrEmpty(str) ? str : def;
    }

    /**
     * seqFormat("Hello {},{}","World","Java") -> "Hello World,Java"
     *
     * @param message
     * @param args
     * @return
     */
    public static String seqFormat(String message, Object... args) {
        if (isNullOrEmpty(message)) {
            return "";
        }
        if (args == null || args.length == 0) {
            return message;
        }
        Matcher m = SEQ_FORMAT.matcher(message);
        int end = 0;
        StringBuilder sb = new StringBuilder();
        for (Object arg : args) {
            if (!m.find()) {
                break;
            }
            if (m.start() > 0) {
                sb.append(message.substring(end, m.start()));
            }
            sb.append(arg == null ? "" : arg.toString());
            end = m.end();
        }
        return sb.append(message.substring(end)).toString();
    }
}
