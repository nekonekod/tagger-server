package com.nekonekod.tagger.taggerserver.service.impl;

import com.nekonekod.tagger.taggerserver.entity.IllustEntity;
import com.nekonekod.tagger.taggerserver.model.IllustQueryParam;
import com.nekonekod.tagger.taggerserver.model.TFileModel;
import com.nekonekod.tagger.taggerserver.model.assembly.TFileAssembly;
import com.nekonekod.tagger.taggerserver.service.IllustService;
import com.nekonekod.tagger.taggerserver.service.TFileService;
import com.nekonekod.tagger.taggerserver.util.FsWatcher;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author duwenjun
 * @date 2018/1/9
 */
@Log4j2
@Service
public class TFileServiceImpl implements TFileService {

    @Resource
    private IllustService illustService;

    @Override
    public List<TFileModel> queryTFile(IllustQueryParam param) {
        List<IllustEntity> illusts = illustService.query(param);
        log.info("queryTFile found {} illust records", illusts.size());
        return FsWatcher.getInstance().searchThenMap(
                illusts, //illust matched query
                IllustEntity::getSourceId, //key for search
                TFileAssembly::fromIllustAndPath); //build TFile
    }


}
