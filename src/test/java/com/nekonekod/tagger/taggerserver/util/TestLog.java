package com.nekonekod.tagger.taggerserver.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author duwenjun
 * @date 2017/12/29
 */
@Ignore
@Slf4j
public class TestLog {


    @Test
    public void test() {
        log.debug("debug");
        log.info("info");
        log.warn("warn");
        log.error("error");
    }


}
