package com.nekonekod.tagger.taggerserver.util;

import com.alibaba.fastjson.JSONObject;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.nekonekod.tagger.taggerserver.db.SQLiteHelper;
import com.nekonekod.tagger.taggerserver.entity.IllustEntity;
import com.nekonekod.tagger.taggerserver.service.PixivService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

/**
 * @author duwenjun
 * @date 2018/1/5
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class OrmLiteTest {

    @Resource
    private PixivService pixivService;

    @Resource
    private SQLiteHelper sqLiteHelper;


    @Test
    public void insert() throws SQLException, IOException {
        // instantiate the dao
        Dao<IllustEntity, String> illustDao = sqLiteHelper.getDao(IllustEntity.class);

        Path path = Paths.get("data", "raw", "pixivs.json");
        String json = FileUtil.readFileToString(path.toFile(), Charset.defaultCharset());
        List<IllustEntity> illusts = pixivService.parseRawData(json);
        illustDao.create(illusts);
//        entities.parallelStream().forEach(e -> {
//            SerializedLock.run(() -> illustDao.create(e));
//        });
    }

    @Test
    public void query() throws SQLException {
        // instantiate the dao
        Dao<IllustEntity, String> illustDao = sqLiteHelper.getDao(IllustEntity.class);

        List<IllustEntity> all = new QueryBuilder<>(sqLiteHelper.getDatabaseType(), sqLiteHelper.getTableInfo(IllustEntity.class), illustDao)
                .where().like("tags", "%これ%")
                .or().like("tags", "%夕時雨%")
                .query();
        all.forEach(e -> System.out.println(JSONObject.toJSONString(e)));
        System.out.println(all.size());
    }

}
