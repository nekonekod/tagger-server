package com.nekonekod.tagger.taggerserver.constant;

/**
 * @author duwenjun
 * @date 2018/1/2
 */
public enum IllustQueryOperator {

    AND("and"), OR("or");

    private String value;

    IllustQueryOperator(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
