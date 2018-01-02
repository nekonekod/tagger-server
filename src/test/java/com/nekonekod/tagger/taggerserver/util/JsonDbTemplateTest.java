package com.nekonekod.tagger.taggerserver.util;

import com.nekonekod.tagger.taggerserver.db.JsonDbTemplate;
import com.nekonekod.tagger.taggerserver.entity.TestCollection;
import io.jsondb.JsonDBTemplate;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Date;

/**
 * @author duwenjun
 * @date 2017/12/28
 */
@Ignore
public class JsonDbTemplateTest {

    @Test
    public void testNewCollection() {
        JsonDbTemplate.initCollection(TestCollection.class);
    }

    @Test
    public void testInsertOne() {
        TestCollection pojo1 = new TestCollection();
        pojo1.setId(IdUtil.genUUID());
        pojo1.setField1("value1");
        pojo1.setField2(new Date());
        pojo1.setField3(123.2);
        pojo1.setArray(new String[]{"A", "B", "C"});
        JsonDBTemplate template = JsonDbTemplate.getInstance();
        template.insert(pojo1);
    }

}