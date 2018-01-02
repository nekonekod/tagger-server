package com.nekonekod.tagger.taggerserver.entity;

import com.nekonekod.tagger.taggerserver.constant.JsonDBConstant;
import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;
import lombok.Data;

import java.util.Date;

/**
 * @author duwenjun
 * @date 2017/12/28
 */
@Data
@Document(collection = "TestCollection", schemaVersion = JsonDBConstant.SCHEMA_VERSION)
public class TestCollection {

    @Id
    private String id;
    private String field1;
    private Date field2;
    private Double field3;
    private String[] array;
}
