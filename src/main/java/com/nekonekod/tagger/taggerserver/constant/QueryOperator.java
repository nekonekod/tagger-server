package com.nekonekod.tagger.taggerserver.constant;

/**
 * @author duwenjun
 * @date 2018/1/2
 */
public enum QueryOperator {

    AND(" and "), OR(" or ");

    private String value;

    QueryOperator(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
