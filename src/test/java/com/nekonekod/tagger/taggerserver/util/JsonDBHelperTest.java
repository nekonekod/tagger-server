package com.nekonekod.tagger.taggerserver.util;

import com.nekonekod.tagger.taggerserver.db.JsonDBHelper;
import com.nekonekod.tagger.taggerserver.entity.IllustCollection;
import io.jsondb.JsonDBTemplate;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Date;

/**
 * @author duwenjun
 * @date 2017/12/28
 */
@Ignore
public class JsonDBHelperTest {

    @Test
    public void testNewCollection() {
        JsonDBHelper.initCollection(IllustCollection.class);
    }

    @Test
    public void testInsertOne() {
        IllustCollection pojo1 = new IllustCollection();
        pojo1.setId(IdUtil.genUUID());
        pojo1.setTitle("value1");
        pojo1.setUpdateTime(new Date());
        pojo1.setFav(123);
        pojo1.setTags(new String[]{"A", "B", "C"});
        JsonDBTemplate template = JsonDBHelper.getInstance();
        template.insert(pojo1);
    }

}