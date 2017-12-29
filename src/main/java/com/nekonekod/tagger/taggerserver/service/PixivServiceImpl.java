package com.nekonekod.tagger.taggerserver.service;

import com.alibaba.fastjson.JSONObject;
import com.nekonekod.tagger.taggerserver.model.PixivRawHolder;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * @author duwenjun
 * @date 2017/12/29
 */
@Log4j2
@Service
public class PixivServiceImpl implements PixivService {


    @Override
    public void importRawData(String json) {
        PixivRawHolder pixivRawHolder = JSONObject.parseObject(json, PixivRawHolder.class);
        pixivRawHolder.getPixiv().forEach(log::info);
    }
}
