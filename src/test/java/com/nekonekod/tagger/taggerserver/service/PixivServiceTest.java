package com.nekonekod.tagger.taggerserver.service;

import com.alibaba.fastjson.JSONObject;
import com.nekonekod.tagger.taggerserver.constant.IllustQueryOperator;
import com.nekonekod.tagger.taggerserver.entity.IllustCollection;
import com.nekonekod.tagger.taggerserver.model.IllustQueryParam;
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
import java.util.Arrays;
import java.util.List;

/**
 * @author duwenjun
 * @date 2017/12/29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PixivServiceTest {

    @Resource
    private PixivService pixivService;
    @Resource
    private IllustService illustService;

    @Test
    public void parseRawData() throws IOException {
        Path path = Paths.get("data", "raw", "test.json");
        String json = FileUtil.readFileToString(path.toFile(), Charset.defaultCharset());
        pixivService.parseRawData(json).forEach(m -> {
            System.out.println(JSONObject.toJSONString(m));
        });
    }

    @Test
    public void importRawData() throws IOException {
        Path path = Paths.get("data", "raw", "pixivs.json");
        String json = FileUtil.readFileToString(path.toFile(), Charset.defaultCharset());
        List<IllustCollection> illusts = pixivService.parseRawData(json);
        illustService.saveBatch(illusts);
    }

    @Test
    public void removeAll() {
        illustService.removeAll();
    }

    @Test
    public void searchByKeys() {
        IllustQueryParam param = new IllustQueryParam();
        param.setSourceId("52131459");
        param.setTags(Arrays.asList("Kancolle","百合"));
        param.setFav(Arrays.asList(0));
        List<IllustCollection> list = illustService.query(param, IllustQueryOperator.AND);
        list.forEach(m -> System.out.println(JSONObject.toJSONString(m)));
        System.out.println(list.size());
    }

    @Test
    public void renamePixivImageFiles() {
        pixivService.renamePixivImageFiles(
                Paths.get(System.getProperty("user.home"), "Downloads", "fsl").toFile(),
                Paths.get(System.getProperty("user.home"), "Downloads", "fsl", "dup").toFile()
        );
    }

}