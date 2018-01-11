package com.nekonekod.tagger.taggerserver.service.impl;

import com.nekonekod.tagger.taggerserver.entity.IllustEntity;
import com.nekonekod.tagger.taggerserver.model.IllustQueryParam;
import com.nekonekod.tagger.taggerserver.model.TFileModel;
import com.nekonekod.tagger.taggerserver.service.IllustService;
import com.nekonekod.tagger.taggerserver.service.TFileService;
import com.nekonekod.tagger.taggerserver.util.FsWatcher;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author duwenjun
 * @date 2018/1/9
 */
@Log4j2
@Service
public class TFileServiceImpl implements TFileService {

    @Resource
    private FsWatcher fsWatcher;

    @Resource
    private IllustService illustService;

    @Override
    public List<TFileModel> queryTFile(IllustQueryParam param) {
        if (Objects.isNull(param)) return tFiles();
        List<IllustEntity> illusts = illustService.query(param);
        log.info("queryTFile found {} illust records", illusts.size());
        List<TFileModel> tFiles = fsWatcher.matchAndMap(
                illusts, //illust matched query
                IllustEntity::getSourceId, //match: filename like sourceId
                file -> null,//orElse: null
                TFileModel::fromIllustAndFile);
        log.info("tFiles found {} records", tFiles.size());
        return tFiles; //build TFile from illust and file
    }

    @Override
    public List<TFileModel> tFiles() {
        List<IllustEntity> illusts = illustService.queryAll();
        log.info("tFiles found {} illust records", illusts.size());
        List<TFileModel> tfiles = fsWatcher.matchAndMap(
                illusts, //illust matched query
                IllustEntity::getSourceId, //match
                TFileModel::fromFile,//orElse: build TFile from file
                TFileModel::fromIllustAndFile);
        log.info("tFiles found {} records", tfiles.size());
        return tfiles; //build TFile
    }

}
