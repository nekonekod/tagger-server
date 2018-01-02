package com.nekonekod.tagger.taggerserver.service;

import com.nekonekod.tagger.taggerserver.constant.IllustQueryOperator;
import com.nekonekod.tagger.taggerserver.entity.IllustCollection;
import com.nekonekod.tagger.taggerserver.model.IllustQueryParam;

import java.util.List;

/**
 * @author duwenjun
 * @date 2018/1/2
 */
public interface IllustService {
    void saveBatch(List<IllustCollection> illusts);

    List<IllustCollection> query(IllustQueryParam param, IllustQueryOperator operator);

    void removeAll();
}
