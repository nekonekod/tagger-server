package com.nekonekod.tagger.taggerserver.service.impl;

import com.j256.ormlite.dao.Dao;
import com.nekonekod.tagger.taggerserver.db.SQLiteHelper;
import com.nekonekod.tagger.taggerserver.entity.TagEntity;
import com.nekonekod.tagger.taggerserver.exception.BusiLogicException;
import com.nekonekod.tagger.taggerserver.service.TagService;
import com.nekonekod.tagger.taggerserver.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * tag business logic
 *
 * @author duwenjun
 * @date 2018/1/2
 */
@Log4j2
@Service
public class TagServiceImpl implements TagService {

    private static final String CACHE_TAG_IGNORE = "TAG_IGNORE";
    private static final String CACHE_TAG_REMAP = "TAG_REMAP";

    @Resource
    private SQLiteHelper sqLiteHelper;

    private Dao<TagEntity, String> tagDao;

    @PostConstruct
    public void inject() {
        tagDao = sqLiteHelper.getDao(TagEntity.class);
    }

    @Override
    @CacheEvict(cacheNames = CACHE_TAG_IGNORE, allEntries = true)
    public void addIgnore(String name) {
        BusiLogicException.checkNotNull("标签名不能为空", name);
        try {
            TagEntity found = tagDao.queryForId(name);
            if (Objects.nonNull(found) && Objects.nonNull(found.getMapTo())) {
                throw BusiLogicException.create().init("[{}]已经配置了映射关系[{} -> {}]，不能再添加忽略关系", name, found.getName(), found.getMapTo());
            }
            if (!Objects.isNull(found)) {
                throw BusiLogicException.create().init("[{}]已经配置了忽略关系，不需要重复添加", name);
            } else {
                tagDao.create(new TagEntity(name, null));
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw BusiLogicException.create(e);
        }
    }

    @Override
    @CacheEvict(cacheNames = {CACHE_TAG_IGNORE, CACHE_TAG_REMAP}, allEntries = true)
    public void addReMap(String name, String mapTo) {
        BusiLogicException.checkNotNull("标签名不能为空", name);
        BusiLogicException.checkNotNull("映射标签名不能为空", mapTo);
        try {
            TagEntity found = tagDao.queryForId(name);
            if (Objects.isNull(found)) { //insert
                tagDao.create(new TagEntity(name, mapTo));
                return;
            }
            if (!Objects.equals(found.getMapTo(), mapTo)) {//modify
                found.setMapTo(mapTo);
                tagDao.update(found);
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw BusiLogicException.create(e);
        }
    }


    @Override
    @Cacheable(cacheNames = CACHE_TAG_IGNORE)
    public List<String> ignoreTags() {
        log.info("get ignoreTags");
        try {
            return tagDao.queryBuilder().where().isNull("mapTo").query().stream().map(TagEntity::getName).collect(Collectors.toList());
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw BusiLogicException.create(e);
        }
    }

    @Override
    @Cacheable(cacheNames = CACHE_TAG_REMAP)
    public Map<String, String> reMapTags() {
        log.info("get reMapTags");
        try {
            return tagDao.queryBuilder()
                    .where().isNotNull("mapTo").query()
                    .stream()
                    .collect(Collectors.toMap(TagEntity::getName, TagEntity::getMapTo));
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw BusiLogicException.create(e);
        }
    }

    @Override
    @CacheEvict(cacheNames = {CACHE_TAG_IGNORE, CACHE_TAG_REMAP}, allEntries = true)
    public void remove(String name) {
        try {
            if (1 != tagDao.deleteById(name)) {
                throw BusiLogicException.create().init("[{}]删除失败", name);
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw BusiLogicException.create(e);
        }
    }

    @Override
    public List<String> updateTags(List<String> src) {
        BusiLogicException.checkNotNull(null, src);
        return mUpdateTags(src).collect(Collectors.toList());
    }

    @Override
    public String updateTagString(String src) {
        BusiLogicException.checkNotNull(null, src);
        List<String> list = Arrays.asList(("$" + src + "$").split("\\$\\$"));
        return mUpdateTags(list)
                .map(t -> "$" + t + "$")
                .collect(Collectors.joining());
    }

    @Override
    public String updateTagString(List<String> src) {
        BusiLogicException.checkNotNull(null, src);
        return mUpdateTags(src)
                .map(t -> "$" + t + "$")
                .collect(Collectors.joining());
    }

    private Stream<String> mUpdateTags(List<String> src) {
        Map<String, String> reMapTags = reMapTags();
        List<String> ignoreTags = ignoreTags();
        return src //"A","B","C" -> "$A$B$C$"
                .stream()
                .filter(StringUtil::notNullOrEmpty)
                .map(t -> reMapTags.getOrDefault(t, ignoreTags.contains(t) ? null : t))
                .filter(Objects::nonNull)
                .distinct();
    }


}
