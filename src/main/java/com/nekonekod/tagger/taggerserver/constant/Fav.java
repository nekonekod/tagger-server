package com.nekonekod.tagger.taggerserver.constant;

/**
 * @author duwenjun
 * @date 2018/1/3
 */
public enum Fav {

    UNKNOW(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    private Integer value;

    Fav(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static Fav getMax() {
        return FIVE;
    }

}
