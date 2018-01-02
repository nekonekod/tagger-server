package com.nekonekod.tagger.taggerserver.entity;

import com.nekonekod.tagger.taggerserver.constant.JsonDBConstant;
import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;
import lombok.Data;

import java.util.Date;

/**
 * Illust
 *
 * @author duwenjun
 * @date 2018/1/2
 */
@Data
@Document(collection = "IllustCollection", schemaVersion = JsonDBConstant.SCHEMA_VERSION)
public class IllustCollection {

    @Id
    private String id;
    private String source;
    private String sourceId;
    private String author;
    private String authorId;
    private String[] tags;
    private Date updateTime;
    private String comment;
    private String title;
    private Integer fav;

}
