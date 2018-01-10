package com.nekonekod.tagger.taggerserver;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.DatabaseTable;
import com.nekonekod.tagger.taggerserver.db.JsonDBHelper;
import com.nekonekod.tagger.taggerserver.db.SQLiteHelper;
import com.nekonekod.tagger.taggerserver.entity.ConfigEntity;
import io.jsondb.annotation.Document;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @author duwenjun
 * @date 2018/1/2
 */
@Component
public class ApplicationHelper {

    @Resource
    private SQLiteHelper sqLiteHelper;

    @PostConstruct
    public void init() throws SQLException {
        initJsonDb();
        initSQLiteDb();
    }

    private void initSQLiteDb() throws SQLException {
        Reflections reflections = new Reflections("com.nekonekod.tagger.taggerserver.entity", new SubTypesScanner(false));
        reflections.getSubTypesOf(Object.class)
                .stream()
                .filter(cls -> Objects.nonNull(cls.getDeclaredAnnotation(DatabaseTable.class)))
                .forEach(sqLiteHelper::createTableIfNotExists);
        Dao<ConfigEntity, String> configDao = sqLiteHelper.getDao(ConfigEntity.class);
        configDao.createIfNotExists(new ConfigEntity("fswatcher.ext", "jpg,jpeg,gif,png", "筛选文件格式", null));
    }

    private void initJsonDb() {
        Reflections reflections = new Reflections("com.nekonekod.tagger.taggerserver.entity", new SubTypesScanner(false));
        reflections.getSubTypesOf(Object.class)
                .stream()
                .filter(cls -> Objects.nonNull(cls.getDeclaredAnnotation(Document.class)))
                .forEach(JsonDBHelper::initCollection);
    }

}
