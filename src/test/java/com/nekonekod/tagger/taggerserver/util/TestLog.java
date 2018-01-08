package com.nekonekod.tagger.taggerserver.util;

import lombok.extern.log4j.Log4j2;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author duwenjun
 * @date 2017/12/29
 */
@Ignore
@Log4j2
public class TestLog {


    @Test
    public void test() {
        log.debug("debug");
        log.info("info");
        log.warn("warn");
        log.error("error");
    }


}
