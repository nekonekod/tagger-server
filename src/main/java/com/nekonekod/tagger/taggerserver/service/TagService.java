package com.nekonekod.tagger.taggerserver.service;

import java.util.List;
import java.util.Map;

/**
 * @author duwenjun
 * @date 2018/1/2
 */
public interface TagService {

    List<String> ignoreTags();

    Map<String, String> reMapTags();
}
