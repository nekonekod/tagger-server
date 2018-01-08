package com.nekonekod.tagger.taggerserver.db;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.table.TableInfo;
import com.j256.ormlite.table.TableUtils;
import com.nekonekod.tagger.taggerserver.annotation.WhereField;
import com.nekonekod.tagger.taggerserver.constant.QueryMatcher;
import com.nekonekod.tagger.taggerserver.constant.QueryOperator;
import com.nekonekod.tagger.taggerserver.entity.IllustEntity;
import com.nekonekod.tagger.taggerserver.exception.BusiLogicException;
import com.nekonekod.tagger.taggerserver.util.CollectionUtil;
import com.nekonekod.tagger.taggerserver.util.ReflectUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * @author duwenjun
 * @date 2018/1/8
 */
@Log4j2
@Component
public class SQLiteHelper {

    @Value("${sqlite.databaseUrl}")
    private String databaseUrl;

    private JdbcPooledConnectionSource connectionSource;

    private final LoadingCache<Class<?>, Dao<?, ?>> daoCache = CacheBuilder.newBuilder()
            .build(new CacheLoader<Class<?>, Dao<?, ?>>() {
                @Override
                public Dao<?, ?> load(Class<?> key) throws Exception {
                    return DaoManager.createDao(connectionSource, key);
                }
            });

    @PostConstruct
    public void init() {
        try {
            connectionSource = new JdbcPooledConnectionSource(databaseUrl);
            install();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void install() throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, IllustEntity.class);
    }

    /**
     * @param table class of table
     * @param <T>   table type
     * @param <ID>  id type
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T, ID> Dao<T, ID> getDao(Class<T> table) {
        try {
            return (Dao<T, ID>) daoCache.get(table);
        } catch (ExecutionException e) {
            log.error(e.getMessage(), e);
            throw BusiLogicException.create(e);
        }
    }

    public DatabaseType getDatabaseType() {
        return connectionSource.getDatabaseType();
    }

    public <T, ID> TableInfo<T, ID> getTableInfo(Class<T> entity) {
        try {
            return new TableInfo<>(connectionSource, null, entity);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw BusiLogicException.create(e);
        }
    }

    /**
     * build where statement
     *
     * @param builder        dao.queryBuilder /dao whereBuilder ...
     * @param annotatedParam {@link WhereField}
     * @param operator       {@link QueryOperator}
     * @param <T>            table type
     * @param <ID>           id type
     * @return
     */
    public <T, ID> Where buildWhere(QueryBuilder<T, ID> builder, Object annotatedParam, QueryOperator operator) {
        Where<T, ID> where = builder.where();
        Field[] fields = annotatedParam.getClass().getDeclaredFields();
        Long numClause = Arrays.stream(fields).map(field -> {
            WhereField annotation = field.getDeclaredAnnotation(WhereField.class);
            if (annotation == null)
                return null;
            Object value = ReflectUtil.getField(field.getName(), annotatedParam);
            if (value == null || (value instanceof Collection && CollectionUtil.isEmpty((Collection) value)))
                return null;
            where(where, field.getName(), value, annotation);
            return true;
        }).filter(Objects::nonNull).count();
        if (operator == QueryOperator.AND) {
            where.and(numClause.intValue());
        } else if (operator == QueryOperator.OR) {
            where.or(numClause.intValue());
        }
        return where;
    }

    private <T, ID> void where(Where<T, ID> where, String name, Object value, WhereField annotation) {
        name = annotation.fieldName().length == 0 ? name : annotation.fieldName()[0];
        try {
            if (annotation.listOperator().length > 0 && annotation.listOperator()[0] == QueryOperator.AND) {
                Collection<?> v = (Collection<?>) value;
                for (Object item : v)
                    where(where, name, item, annotation.matcher());
                where.and(v.size());
            } else if (annotation.listOperator().length > 0 && annotation.listOperator()[0] == QueryOperator.OR) {
                Collection<?> v = (Collection<?>) value;
                for (Object item : v)
                    where(where, name, item, annotation.matcher());
                where.or(v.size());
            } else {
                where(where, name, value, annotation.matcher());
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

    private <T, ID> void where(Where<T, ID> where, String name, Object value, QueryMatcher matcher) throws SQLException {
        if (matcher == QueryMatcher.Contains) {
            where.like(name, "%" + value + "%");
        } else if (matcher == QueryMatcher.Equals) {
            where.eq(name, value);
        }
    }

}
