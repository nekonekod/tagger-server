package com.nekonekod.tagger.taggerserver.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Illust
 * for OrmLite
 *
 * @author duwenjun
 * @date 2018/1/2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@DatabaseTable(tableName = "illust")
public class IllustEntity {

    @DatabaseField(id = true)
    private String id;
    @DatabaseField
    private String source;
    @DatabaseField
    private String sourceId;
    @DatabaseField
    private String author;
    @DatabaseField
    private String authorId;
    @DatabaseField
    private String tags;
    @DatabaseField
    private Date updateTime;
    @DatabaseField
    private String comment;
    @DatabaseField
    private String title;
    @DatabaseField
    private Integer fav;

}
