package com.nekonekod.tagger.taggerserver.util;

import lombok.Data;

/**
 * Created by HzQ on 16/9/9.
 */
@Data
public class AjaxMsg {
    public static enum CODE {
        DEFAULT
    }

    public static final int INCOMPLETE_PARAM = 100;

    private int code;
    private String type;
    private String message;

    public AjaxMsg(int code, String type, String message) {
        this.code = code;
        this.type = type;
        this.message = message;
    }
}
