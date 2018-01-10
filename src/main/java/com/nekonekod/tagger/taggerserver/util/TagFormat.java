package com.nekonekod.tagger.taggerserver.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author duwenjun
 * @date 2018/1/10
 */
public class TagFormat {

    public static List<String> fromTagString(String tag) {
        if (StringUtil.isNullOrEmpty(tag)) return Collections.emptyList();
        return Arrays.asList(("$" + tag + "$").split("\\$\\$"));
    }
}
