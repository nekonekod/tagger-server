package com.nekonekod.tagger.taggerserver.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.nekonekod.tagger.taggerserver.constant.IllustSource;
import com.nekonekod.tagger.taggerserver.entity.IllustEntity;
import com.nekonekod.tagger.taggerserver.model.PixivRawHolder;
import com.nekonekod.tagger.taggerserver.model.PixivRawModel;
import com.nekonekod.tagger.taggerserver.service.PixivService;
import com.nekonekod.tagger.taggerserver.service.TagService;
import com.nekonekod.tagger.taggerserver.util.DateUtil;
import com.nekonekod.tagger.taggerserver.util.FileUtil;
import com.nekonekod.tagger.taggerserver.util.IdUtil;
import com.nekonekod.tagger.taggerserver.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author duwenjun
 * @date 2017/12/29
 */
@Log4j2
@Service
public class PixivServiceImpl implements PixivService {

    @Value("${pixiv.cleanDirtyRaw}")
    private boolean cleanDirtyRaw;

    @Resource
    private TagService tagService;

    @Override
    public List<IllustEntity> parseRawData(String json) {
        PixivRawHolder pixivRawHolder = JSONObject.parseObject(json, PixivRawHolder.class);
        return pixivRawHolder.getPixiv().parallelStream().map(m -> {
            IllustEntity illust = new IllustEntity();
            if (cleanDirtyRaw) cleanDirtyRaw(m);
            illust.setId(IdUtil.genUUID());
            illust.setSource(IllustSource.PIXIV.getValue());
            illust.setSourceId(m.getId());
            illust.setAuthor(m.getAuthor());
            illust.setAuthorId(m.getAuthorId());
            illust.setTags(tagService.updateTagString(m.getTags()));
            illust.setUpdateTime(new Date(Long.valueOf(m.getDate())));
            illust.setComment(null);
            illust.setTitle(m.getTitle());
            illust.setFav(0);
            return illust;
        }).collect(Collectors.toList());
    }

    private void cleanDirtyRaw(PixivRawModel rawModel) {
        if (Objects.isNull(rawModel)) return;
        /* date格式
         * 2017-11-17T17:59:47.486Z -> 1510941587486
         * 2017年11月17日 17:59 -> 1510912740000
         */
        if (Objects.isNull(rawModel.getDate())) rawModel.setDate(String.valueOf(new Date().getTime()));
        if (!StringUtil.isNumeric(rawModel.getDate())) {
            Date date = rawModel.getDate().contains("年") ?
                    DateUtil.parseChineseFormat(rawModel.getDate()) :
                    DateUtil.parseDateTimeFormat(rawModel.getDate());
            rawModel.setDate(String.valueOf(date.getTime()));
        }

        //author和id颠倒
        if (StringUtil.isNumeric(rawModel.getAuthor())) {
            String author = rawModel.getAuthor();
            rawModel.setAuthor(rawModel.getAuthorId());
            rawModel.setAuthorId(author);
        }
    }

    @Override
    public void renamePixivImageFiles(File dir, File dupDir) {
        if (!FileUtil.isValidDir(dir) || Objects.isNull(dupDir)) {
            log.error("invalid dir:{},dupDir", dir, dupDir);
            return;
        }
        if (!dupDir.exists() || !dupDir.isDirectory()) dupDir.mkdirs();
        FileUtil.listFiles(dir, new String[]{"jpg", "png"}, true).parallelStream().forEach(f -> {
            String name = f.getName();
            final String mark = "_p.";
            if (name.contains(mark)) {  //need rename
                String newName = name.replace(mark, ".");
                File newFile = Paths.get(f.getParent(), newName).toFile();
                if (newFile.exists()) {
                    log.warn("file dup exists:{}", f.getAbsolutePath());
                    //move to dup
                    newFile = Paths.get(dupDir.toPath().toString(), newName).toFile();
                }
                if (f.renameTo(newFile)) {
                    log.info("move file {} to {}", f, newFile);
                } else {
                    log.error("error: move file {} to {}", f, newFile);
                }
            }
        });
        if (FileUtil.isEmptyDir(dir)) dupDir.delete();
    }
}
