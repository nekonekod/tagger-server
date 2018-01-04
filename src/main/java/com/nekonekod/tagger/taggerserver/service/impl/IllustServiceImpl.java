package com.nekonekod.tagger.taggerserver.service.impl;

import com.nekonekod.tagger.taggerserver.constant.JxQueryOperator;
import com.nekonekod.tagger.taggerserver.db.JsonDBHelper;
import com.nekonekod.tagger.taggerserver.entity.IllustCollection;
import com.nekonekod.tagger.taggerserver.model.IllustQueryParam;
import com.nekonekod.tagger.taggerserver.service.IllustService;
import com.nekonekod.tagger.taggerserver.util.JxQueryUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<IllustCollection> query(IllustQueryParam param, JxQueryOperator operator) {
        String query = JxQueryUtil.buildJxQuery(IllustQueryParam.class, param, operator);
        log.info("jxQuery:{}", query);
        return JsonDBHelper.getInstance().find(query, IllustCollection.class);
    }

    @Override
    public void removeAll() {
        JsonDBHelper.getInstance().findAllAndRemove(".", IllustCollection.class);
    }

    public static void main(String[] args) {
    }

}
