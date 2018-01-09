package com.nekonekod.tagger.taggerserver.service;

import java.util.List;
import java.util.Map;

/**
 * @author duwenjun
 * @date 2018/1/2
 */
public interface TagService {

    /**
     * add ignore
     *
     * @param name
     */
    void addIgnore(String name);

    /**
     * add reMap name -> mapTp
     *
     * @param name
     * @param mapTo
     */
    void addReMap(String name, String mapTo);

    /**
     * tag which will be ignored (to database)
     *
     * @return
     */
    List<String> ignoreTags();

    /**
     * the {key} tag will be ignored but the {value} tag will be saved instead
     *
     * @return
     */
    Map<String, String> reMapTags();

    /**
     * remove by name
     *
     * @param name
     */
    void remove(String name);

    List<String> updateTags(List<String> src);

    String updateTagString(String src);

    String updateTagString(List<String> src);
}
