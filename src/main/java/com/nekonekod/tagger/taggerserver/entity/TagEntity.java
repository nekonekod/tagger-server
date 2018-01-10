package com.nekonekod.tagger.taggerserver.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author duwenjun
 * @date 2018/1/9
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@DatabaseTable(tableName = "tag")
public class TagEntity {

    @DatabaseField(id = true)
    private String name;
    @DatabaseField
    private String mapTo;

}
