package com.nekonekod.tagger.taggerserver.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Data;

/**
 * @author duwenjun
 * @date 2018/1/9
 */
@Data
@DatabaseTable(tableName = "tag")
public class TagEntity {

    @DatabaseField(id = true)
    private String name;
    @DatabaseField
    private String mapTo;

    public TagEntity() {
    }

    public TagEntity(String name, String mapTo) {
        this.name = name;
        this.mapTo = mapTo;
    }
}
