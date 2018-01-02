package com.nekonekod.tagger.taggerserver.service.impl;

import com.google.common.collect.ImmutableMap;
import com.nekonekod.tagger.taggerserver.service.TagService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author duwenjun
 * @date 2018/1/2
 */
@Log4j2
@Service
public class TagServiceImpl implements TagService {

    @Override
    public List<String> ignoreTags() {
        return Arrays.asList("艦これ500users入り");
    }

    @Override
    public Map<String, String> reMapTags() {
        return ImmutableMap.of("艦これ", "Kancolle",
                "艦これかわいい","Kancolle");
    }
}
