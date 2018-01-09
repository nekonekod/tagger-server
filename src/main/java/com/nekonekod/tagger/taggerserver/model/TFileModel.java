package com.nekonekod.tagger.taggerserver.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

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

}
