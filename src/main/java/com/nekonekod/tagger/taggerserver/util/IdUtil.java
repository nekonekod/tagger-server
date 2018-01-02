package com.nekonekod.tagger.taggerserver.util;

import java.util.UUID;

/**
 * @author duwenjun
 * @date 2017/12/28
 */
public class IdUtil {

    /**
     * 生成UUID，大写
     *
     * @return
     */
    public static String genUUID() {
        return UUID.randomUUID().toString().toUpperCase();
    }
}
