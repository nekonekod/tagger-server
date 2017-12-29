package com.nekonekod.tagger.taggerserver.util;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author duwenjun
 * @date 2017/12/28
 */
@Ignore
public class StringUtilTest {
    @Test
    public void join() throws Exception {
        assert "A,B,C".equals(StringUtil.join(",", "A", "B", null, "C"));
    }

    @Test
    public void join1() throws Exception {
        assert "A,B,C".equals(StringUtil.join(",", Arrays.asList("A", "B", null, "C")));
    }

    @Test
    public void lowerUnderscoreToLowerCamel() throws Exception {
        assert "abcAbc".equals(StringUtil.lowerUnderscoreToLowerCamel("abc_abc"));
    }

    @Test
    public void lowerCamelToLowerUnderscore() throws Exception {
        assert "abc_abc".equals(StringUtil.lowerCamelToLowerUnderscore("abcAbc"));
    }

    @Test
    public void upperUnderscoreToUpperCamel() throws Exception {
        assert "AbcAbc".equals(StringUtil.upperUnderscoreToUpperCamel("ABC_ABC"));
    }

    @Test
    public void upperCamelToUpperUnderscore() throws Exception {
        assert "ABC_ABC".equals(StringUtil.upperCamelToUpperUnderscore("AbcAbc"));
    }

}