package com.nekonekod.tagger.taggerserver.service;

import java.util.List;
import java.util.Map;

/**
 * @author duwenjun
 * @date 2018/1/2
 */
public interface TagService {

    /**
     * tag which will be ignored (to database)
     *
     * @return
     */
    List<String> ignoreTags();

    /**
     * the key tag will be ignored but the value tag will be saved instead
     *
     * @return
     */
    Map<String, String> reMapTags();
}
