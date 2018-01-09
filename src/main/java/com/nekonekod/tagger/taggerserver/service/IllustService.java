package com.nekonekod.tagger.taggerserver.service;

import com.nekonekod.tagger.taggerserver.entity.IllustEntity;
import com.nekonekod.tagger.taggerserver.model.IllustQueryParam;
import com.nekonekod.tagger.taggerserver.model.PagedList;
import com.nekonekod.tagger.taggerserver.model.Paging;

import java.util.List;

/**
 * @author duwenjun
 * @date 2018/1/2
 */
public interface IllustService {

    void saveBatch(List<IllustEntity> illusts);

    List<IllustEntity> query(IllustQueryParam param);

    void removeAll();

}
