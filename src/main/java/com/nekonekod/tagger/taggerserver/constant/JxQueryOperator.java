package com.nekonekod.tagger.taggerserver.constant;

/**
 * @author duwenjun
 * @date 2018/1/2
 */
public enum JxQueryOperator {

    AND(" and "), OR(" or "), NULL(null);

    private String value;

    JxQueryOperator(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
