package com.nekonekod.tagger.taggerserver.service;

import com.alibaba.fastjson.JSONObject;
import com.nekonekod.tagger.taggerserver.constant.QueryOperator;
import com.nekonekod.tagger.taggerserver.model.IllustQueryParam;
import com.nekonekod.tagger.taggerserver.model.TFileModel;
import com.nekonekod.tagger.taggerserver.util.FsWatcher;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author duwenjun
 * @date 2018/1/9
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class TFileServiceTest {

    @Resource
    private FsWatcher fsWatcher;

    @Resource
    private TFileService tFileService;

    @Test
    public void queryTFile() throws Exception {
        fsWatcher.registerDir(Paths.get("/Users/nekod/Downloads/fs").toFile());
        IllustQueryParam param = new IllustQueryParam();
//        param.setSourceId("62491647");
//        param.setTags(Arrays.asList("夕時雨"));
//        param.setAuthor("トリエ");
//        param.setAuthorId("10233886");
//        param.setTitle("雨");
//        param.setFav(Arrays.asList(1,2,3,4));
        param.setOperator(QueryOperator.AND);
        List<TFileModel> list = tFileService.queryTFile(param);
        list.stream().map(JSONObject::toJSONString).forEach(System.out::println);
    }

}