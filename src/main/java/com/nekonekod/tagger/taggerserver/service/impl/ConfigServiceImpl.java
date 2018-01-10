package com.nekonekod.tagger.taggerserver.service.impl;

import com.j256.ormlite.dao.Dao;
import com.nekonekod.tagger.taggerserver.db.SQLiteHelper;
import com.nekonekod.tagger.taggerserver.entity.ConfigEntity;
import com.nekonekod.tagger.taggerserver.service.ConfigService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author duwenjun
 * @date 2018/1/10
 */
@Log4j2
@Service
public class ConfigServiceImpl implements ConfigService {

    @Resource
    private SQLiteHelper sqLiteHelper;

    Dao<ConfigEntity, String> configDao;


    @PostConstruct
    public void inject() {
        configDao = sqLiteHelper.getDao(ConfigEntity.class);
    }


    @Override
    public List<String> fsWatcherExt() {
        try {
            ConfigEntity rest = configDao.queryForId("fswatcher.ext");
            return Optional.ofNullable(rest).map(ConfigEntity::getValue)
                    .map(v -> Arrays.asList(v.split(",")))
                    .orElse(Collections.emptyList());
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }
}
