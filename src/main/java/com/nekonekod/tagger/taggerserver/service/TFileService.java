package com.nekonekod.tagger.taggerserver.service;

import com.nekonekod.tagger.taggerserver.model.IllustQueryParam;
import com.nekonekod.tagger.taggerserver.model.TFileModel;

import java.util.List;

/**
 * tagged file business logic
 *
 * @author duwenjun
 * @date 2018/1/9
 */
public interface TFileService {

    /**
     * query matched files
     *
     * @param param
     * @return
     */
    List<TFileModel> queryTFile(IllustQueryParam param);
}
