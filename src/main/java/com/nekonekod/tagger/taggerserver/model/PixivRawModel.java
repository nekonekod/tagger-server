package com.nekonekod.tagger.taggerserver.model;

import lombok.Data;

import java.util.List;

/**
 * @author duwenjun
 * @date 2017/12/29
 */
@Data
public class PixivRawModel {

    private String id;
    private String title;
    private String date;
    private List<String> tags;
    private String author;
    private String authorId;

}
