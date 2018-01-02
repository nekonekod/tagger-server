package com.nekonekod.tagger.taggerserver.service.impl;

import com.nekonekod.tagger.taggerserver.constant.IllustQueryOperator;
import com.nekonekod.tagger.taggerserver.db.JsonDBHelper;
import com.nekonekod.tagger.taggerserver.entity.IllustCollection;
import com.nekonekod.tagger.taggerserver.model.IllustQueryParam;
import com.nekonekod.tagger.taggerserver.service.IllustService;
import com.nekonekod.tagger.taggerserver.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author duwenjun
 * @date 2018/1/2
 */
@Log4j2
@Service
public class IllustServiceImpl implements IllustService {

    @Override
    public void saveBatch(List<IllustCollection> illusts) {
        JsonDBHelper.getInstance().insert(illusts, IllustCollection.class);
    }

    @Override
    public List<IllustCollection> query(IllustQueryParam param, IllustQueryOperator operator) {
        String query = buildQuery(param, operator);
        log.info("query:{}", query);
        return JsonDBHelper.getInstance().find(query, IllustCollection.class);
    }

    private static String buildQuery(IllustQueryParam param, IllustQueryOperator operator) {
        String delimiter = " " + Optional.ofNullable(operator).orElse(IllustQueryOperator.OR).getValue() + " ";

        String sourceIdQuery = Optional.ofNullable(param.getSourceId())
                .map(sourceId -> String.format(" sourceId=%s ", sourceId))
                .orElse(null);

        String tagQuery = StringUtil.trimToNull(
                Optional.ofNullable(param.getTags()).orElse(Collections.emptyList())
                        .stream()
                        .filter(tag -> !StringUtil.isNullOrEmpty(tag))
                        .distinct()
                        .map(tag -> String.format(" ./tags[contains(.,'%s')] ", tag)) //模糊匹配
                        .collect(Collectors.joining(delimiter))
        );

        String favQuery = StringUtil.trimToNull(
                Optional.ofNullable(param.getFav()).orElse(Collections.emptyList())
                        .stream()
                        .map(f -> String.format(" fav=%d ", Integer.valueOf(f))) //模糊匹配
                        .collect(Collectors.joining(delimiter))
        );

        String query = StringUtil.join(delimiter, sourceIdQuery, tagQuery, favQuery);
        return "/.[" + query + "]";
    }

    @Override
    public void removeAll() {
        JsonDBHelper.getInstance().findAllAndRemove(".", IllustCollection.class);
    }

    public static void main(String[] args) {
    }

}
