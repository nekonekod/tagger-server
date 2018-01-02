package com.nekonekod.tagger.taggerserver.service;

import com.alibaba.fastjson.JSONObject;
import com.nekonekod.tagger.taggerserver.util.FileUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author duwenjun
 * @date 2017/12/29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PixivServiceTest {

    @Resource
    private PixivService pixivService;

    @Test
    public void importRawData() throws IOException {
        Path path = Paths.get("data", "raw", "test.json");
        String json = FileUtil.readFileToString(path.toFile(), Charset.defaultCharset());
        pixivService.parseRawData(json).forEach(m -> {
            System.out.println(JSONObject.toJSONString(m));
        });
    }

    @Test
    public void renamePixivImageFiles() {
        pixivService.renamePixivImageFiles(
                Paths.get(System.getProperty("user.home"), "Downloads", "fsl").toFile(),
                Paths.get(System.getProperty("user.home"), "Downloads", "fsl", "dup").toFile()
        );
    }

}