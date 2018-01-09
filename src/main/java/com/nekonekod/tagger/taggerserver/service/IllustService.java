package com.nekonekod.tagger.taggerserver.service;

import com.nekonekod.tagger.taggerserver.entity.IllustEntity;
import com.nekonekod.tagger.taggerserver.model.IllustQueryParam;

import java.util.List;

/**
 * @author duwenjun
 * @date 2018/1/2
 */
public interface IllustService {

    void save(List<IllustEntity> illusts);

    List<IllustEntity> query(IllustQueryParam param);

    void removeAll();

    void updateTags();

}
