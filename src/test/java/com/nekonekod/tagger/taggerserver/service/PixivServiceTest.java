package com.nekonekod.tagger.taggerserver.service;

import com.alibaba.fastjson.JSONObject;
import com.nekonekod.tagger.taggerserver.constant.QueryOperator;
import com.nekonekod.tagger.taggerserver.entity.IllustEntity;
import com.nekonekod.tagger.taggerserver.model.IllustQueryParam;
import com.nekonekod.tagger.taggerserver.model.PagedList;
import com.nekonekod.tagger.taggerserver.model.Paging;
import com.nekonekod.tagger.taggerserver.util.FileUtil;
import org.junit.Ignore;
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
@Ignore
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
        List<IllustEntity> illusts = pixivService.parseRawData(json);
        illustService.saveBatch(illusts);
    }

    @Test
    public void removeAll() {
        illustService.removeAll();
    }

    @Test
    public void searchByKeys() {
        IllustQueryParam param = new IllustQueryParam();
//        param.setSourceId("52131459");
        param.setTags(Arrays.asList("Kanco", "夕時雨"));
//        param.setAuthor("トリエ");
//        param.setAuthorId("10233886");
        param.setTitle("雨");
//        param.setFav(Arrays.asList(1,2,3,4));
        PagedList<IllustEntity> pagedList = illustService.query(param, QueryOperator.AND, new Paging(1, 5));
        pagedList.getPageList().forEach(m -> System.out.println(m.getSourceId() + ":" + JSONObject.toJSONString(m.getTags())));
        System.out.println(pagedList.getPage());
    }

    @Test
    public void renamePixivImageFiles() {
        pixivService.renamePixivImageFiles(
                Paths.get(System.getProperty("user.home"), "Downloads", "fsl").toFile(),
                Paths.get(System.getProperty("user.home"), "Downloads", "fsl", "dup").toFile()
        );
    }

}