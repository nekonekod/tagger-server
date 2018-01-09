package com.nekonekod.tagger.taggerserver.model;

import com.j256.ormlite.field.DatabaseField;
import lombok.Data;

/**
 * @author duwenjun
 * @date 2018/1/9
 */
@Data
public class TagParam {


    @DatabaseField(id = true)
    private String name;
    @DatabaseField
    private String mapTo;
}
