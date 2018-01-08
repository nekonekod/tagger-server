package com.nekonekod.tagger.taggerserver.service.impl;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.nekonekod.tagger.taggerserver.constant.QueryOperator;
import com.nekonekod.tagger.taggerserver.db.SQLiteHelper;
import com.nekonekod.tagger.taggerserver.entity.IllustEntity;
import com.nekonekod.tagger.taggerserver.exception.BusiLogicException;
import com.nekonekod.tagger.taggerserver.model.IllustQueryParam;
import com.nekonekod.tagger.taggerserver.model.PagedList;
import com.nekonekod.tagger.taggerserver.model.Paging;
import com.nekonekod.tagger.taggerserver.service.IllustService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

/**
 * @author duwenjun
 * @date 2018/1/2
 */
@Log4j2
@Service
public class IllustServiceImpl implements IllustService {

    @Resource
    private SQLiteHelper sqLiteHelper;

    private Dao<IllustEntity, String> illustDao;

    @PostConstruct
    public void inject() {
        illustDao = sqLiteHelper.getDao(IllustEntity.class);
    }

    @Override
    public void saveBatch(List<IllustEntity> illusts) {
        try {
            illustDao.create(illusts);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw BusiLogicException.create(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public PagedList<IllustEntity> query(IllustQueryParam param, QueryOperator operator, Paging paging) {
        param.selfClean();
        QueryBuilder<IllustEntity, String> builder = illustDao.queryBuilder();
        Where<IllustEntity, String> where = sqLiteHelper.buildWhere(builder, param, operator);
        builder.setWhere(where);
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
    public List<IllustEntity> query(IllustQueryParam param, QueryOperator operator) {
        return query(param, operator, new Paging(1, Integer.MAX_VALUE)).getPageList();
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

}
