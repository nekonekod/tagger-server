package com.nekonekod.tagger.taggerserver.service.impl;

import com.nekonekod.tagger.taggerserver.service.TagService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * tag business logic
 *
 * @author duwenjun
 * @date 2018/1/2
 */
@Log4j2
@Service
public class TagServiceImpl implements TagService {

    @Override
    public List<String> ignoreTags() {
        return Collections.emptyList();
    }

    @Override
    public Map<String, String> reMapTags() {
        return Collections.emptyMap();
    }
}
