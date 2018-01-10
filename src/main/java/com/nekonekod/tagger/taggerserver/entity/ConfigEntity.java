package com.nekonekod.tagger.taggerserver.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author duwenjun
 * @date 2018/1/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@DatabaseTable(tableName = "config")
public class ConfigEntity {

    @DatabaseField(id = true)
    private String name;
    @DatabaseField
    private String value;
    @DatabaseField
    private String desc;
    @DatabaseField
    private String extra;

}
