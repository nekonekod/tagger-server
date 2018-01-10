package com.nekonekod.tagger.taggerserver.service;

import com.nekonekod.tagger.taggerserver.constant.QueryOperator;
import com.nekonekod.tagger.taggerserver.entity.IllustEntity;
import com.nekonekod.tagger.taggerserver.model.IllustQueryParam;

import java.util.List;

/**
 * @author duwenjun
 * @date 2018/1/2
 */
public interface IllustService {

    /**
     * batch insert
     *
     * @param illusts
     */
    void save(List<IllustEntity> illusts);

    /**
     * query
     *
     * @param param
     * @return
     */
    List<IllustEntity> query(IllustQueryParam param);

    /**
     * remove all
     */
    void removeAll();

    /**
     * update Tags in db
     */
    void updateTags();

}
