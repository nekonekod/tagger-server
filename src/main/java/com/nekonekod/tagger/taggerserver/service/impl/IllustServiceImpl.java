package com.nekonekod.tagger.taggerserver.service.impl;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.nekonekod.tagger.taggerserver.db.SQLiteHelper;
import com.nekonekod.tagger.taggerserver.entity.IllustEntity;
import com.nekonekod.tagger.taggerserver.exception.BusiLogicException;
import com.nekonekod.tagger.taggerserver.model.IllustQueryParam;
import com.nekonekod.tagger.taggerserver.model.PagedList;
import com.nekonekod.tagger.taggerserver.model.Paging;
import com.nekonekod.tagger.taggerserver.service.IllustService;
import com.nekonekod.tagger.taggerserver.service.TagService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * @author duwenjun
 * @date 2018/1/2
 */
@Log4j2
@Service
public class IllustServiceImpl implements IllustService {

    @Resource
    private TagService tagService;

    @Resource
    private SQLiteHelper sqLiteHelper;

    private Dao<IllustEntity, String> illustDao;

    @PostConstruct
    public void inject() {
        illustDao = sqLiteHelper.getDao(IllustEntity.class);
    }

    @Override
    public void save(List<IllustEntity> illusts) {
        try {
            illustDao.create(illusts);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw BusiLogicException.create(e);
        }
    }

    @SuppressWarnings("unchecked")
    private PagedList<IllustEntity> query(IllustQueryParam param, Paging paging) {
        QueryBuilder<IllustEntity, String> builder = illustDao.queryBuilder();
        sqLiteHelper.buildAndSetWhere(builder, param, param.getOperator());
        builder.orderBy("id", true);
        try {
            Long count = builder.countOf();
            builder.limit(paging.getPageSize());
            builder.offset(paging.getOffset());
            String s = builder.prepareStatementString();
            log.info("where statement:{}", s);
            List<IllustEntity> rest = builder.query();
            return new PagedList<>(rest, paging.resetTotalCount(count));
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw BusiLogicException.create(e);
        }
    }

    @Override
    public List<IllustEntity> query(IllustQueryParam param) {
        return query(param, new Paging(1, Integer.MAX_VALUE)).getPageList();
    }

    @Override
    public List<IllustEntity> queryAll() {
        try {
            return illustDao.queryForAll();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw BusiLogicException.create(e);
        }
    }

    @Override
    public void removeAll() {
        try {
            illustDao.deleteBuilder().delete();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw BusiLogicException.create(e);
        }
    }

    @Override
    public void updateTags() {
        try {
            List<IllustEntity> all = illustDao.queryForAll();
            for (IllustEntity illust : all) {
                String newTag = tagService.updateTagString(illust.getTags());
                if (!Objects.equals(newTag, illust.getTags())) {
                    illust.setTags(newTag);
                    illustDao.update(illust);
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw BusiLogicException.create(e);
        }
    }

}
