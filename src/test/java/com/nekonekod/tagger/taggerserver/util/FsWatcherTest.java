package com.nekonekod.tagger.taggerserver.util;

import com.alibaba.fastjson.JSONObject;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * @author duwenjun
 * @date 2017/12/29
 */
@Ignore
public class FsWatcherTest {


    @Test
    public void testWatcher() throws IOException {
        FsWatcher fsWatcher = FsWatcher.getInstance();
        fsWatcher.registerDir(new File("/Users/nekod/Downloads"));
        List<Path> result = fsWatcher.search("微软");
        System.out.println(JSONObject.toJSONString(result));
    }

}