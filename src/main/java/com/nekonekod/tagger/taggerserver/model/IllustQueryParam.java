package com.nekonekod.tagger.taggerserver.model;

import lombok.Data;

import java.util.List;

/**
 * @author duwenjun
 * @date 2018/1/2
 */
@Data
public class IllustQueryParam {

    private String source;
    private String sourceId;
    private String author;
    private String authorId;
    private List<String> tags;
    private String comment;
    private String title;
    private List<Integer> fav;

}
