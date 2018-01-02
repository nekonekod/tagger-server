package com.nekonekod.tagger.taggerserver.constant;

/**
 * @author duwenjun
 * @date 2018/1/2
 */
public enum IllustSource {

    PIXIV("pixiv");

    private String value;

    IllustSource(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
