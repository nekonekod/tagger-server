package com.nekonekod.tagger.taggerserver.util;

import com.alibaba.fastjson.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author duwenjun
 * @date 2017/12/29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class FsWatcherTest {

    @Resource
    FsWatcher fsWatcher;

    @Test
    public void testWatcher() throws IOException {
        fsWatcher.registerDir(new File("/Users/nekod/Downloads"));
        List<String> result = fsWatcher.search("微软");
        System.out.println(JSONObject.toJSONString(result));
    }

}