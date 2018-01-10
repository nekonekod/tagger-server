package com.nekonekod.tagger.taggerserver.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.nekonekod.tagger.taggerserver.dto.IllustQueryDto;
import com.nekonekod.tagger.taggerserver.model.IllustQueryParam;
import com.nekonekod.tagger.taggerserver.model.TFileModel;
import com.nekonekod.tagger.taggerserver.service.TFileService;
import com.nekonekod.tagger.taggerserver.util.AjaxResultUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author duwenjun
 * @date 2018/1/10
 */
@Log4j2
@RestController
@RequestMapping("tfile")
public class TFileController {

    @Resource
    private TFileService tFileService;

    @RequestMapping("query")
    public Object query(@RequestBody IllustQueryDto query) {
        log.info("param:{},operator:{}", JSONObject.toJSONString(query), "");
        List<TFileModel> list = tFileService.queryTFile(IllustQueryParam.fromIllustQueryDto(query));
        return AjaxResultUtil.success(list);
    }

}
