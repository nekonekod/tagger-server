package com.nekonekod.tagger.taggerserver.service;

import com.nekonekod.tagger.taggerserver.constant.QueryOperator;
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

    PagedList<IllustEntity> query(IllustQueryParam param, QueryOperator operator, Paging paging);

    List<IllustEntity> query(IllustQueryParam param, QueryOperator operator);

    void removeAll();

}
