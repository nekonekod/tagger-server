package com.nekonekod.tagger.taggerserver.service;

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
    public void testImportRawData() throws IOException {
        Path path = Paths.get("data", "raw", "pixivs.json");
        String json = FileUtil.readFileToString(path.toFile(), Charset.defaultCharset());
        pixivService.importRawData(json);
    }

}