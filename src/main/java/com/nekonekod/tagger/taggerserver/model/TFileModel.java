package com.nekonekod.tagger.taggerserver.model;

import com.nekonekod.tagger.taggerserver.entity.IllustEntity;
import com.nekonekod.tagger.taggerserver.util.FileUtil;
import com.nekonekod.tagger.taggerserver.util.TagFormat;
import lombok.Builder;
import lombok.Data;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author duwenjun
 * @date 2018/1/9
 */
@Data
@Builder
public class TFileModel {

    private String path;
    private String fileName;
    private String illustId;
    private List<String> tags;
    private String source;
    private String sourceId;
    private String author;
    private String authorId;
    private Date updateTime;
    private String comment;
    private String title;
    private Integer fav;


    public static TFileModel fromIllustAndFile(IllustEntity illust, File file) {
        if (Objects.isNull(illust)) return fromFile(file);
        if (!FileUtil.isValidFile(file)) return null;
        return TFileModel.builder()
                .fileName(file.getName())
                .path(file.getAbsolutePath())
                .illustId(illust.getId())
                .source(illust.getSource())
                .sourceId(illust.getSourceId())
                .author(illust.getAuthor())
                .author(illust.getAuthorId())
                .tags(TagFormat.fromTagString(illust.getTags()))
                .updateTime(illust.getUpdateTime())
                .comment(illust.getComment())
                .title(illust.getTitle())
                .build();
    }

    public static TFileModel fromFile(File file) {
        if (!FileUtil.isValidFile(file)) return null;
        return TFileModel.builder()
                .fileName(file.getName())
                .path(file.getAbsolutePath())
                .build();
    }
}
