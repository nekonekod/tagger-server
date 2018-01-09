package com.nekonekod.tagger.taggerserver.model.assembly;

import com.nekonekod.tagger.taggerserver.entity.IllustEntity;
import com.nekonekod.tagger.taggerserver.model.TFileModel;

import java.nio.file.Path;
import java.util.Objects;

/**
 * @author duwenjun
 * @date 2018/1/9
 */
public class TFileAssembly {

    public static TFileModel fromIllustAndPath(IllustEntity illust, Path path) {
        if (Objects.isNull(illust) || Objects.isNull(path)) return null;
        return TFileModel.builder()
                .fileName(path.toFile().getName())
                .path(path.toString())
                .illustId(illust.getId())
                .source(illust.getSource())
                .sourceId(illust.getSourceId())
                .author(illust.getAuthor())
                .author(illust.getAuthorId())
                .updateTime(illust.getUpdateTime())
                .comment(illust.getComment())
                .title(illust.getTitle())
                .build();
    }
}
