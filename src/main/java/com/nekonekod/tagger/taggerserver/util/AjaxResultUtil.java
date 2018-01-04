package com.nekonekod.tagger.taggerserver.util;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuexiaojun on 15/1/29.
 * 遵守规范 http://wiki.sankuai.com/pages/viewpage.action?pageId=118886811
 * 保留"status", 200 是为了兼容以前部分代码
 */
public class AjaxResultUtil {

    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_FAILED = 0;
    public static final int NEED_LOGIN = 401;

    public static final String COLOR_SUCCESS = "success";
    public static final String COLOR_INFO = "info";
    public static final String COLOR_WARNING = "warning";
    public static final String COLOR_ERROR = "error";


    public static Map<String, Object> build(int status, Object data) {
        return build(status, null, data);
    }

    public static Map<String, Object> build(int status, String confirm, Object data) {
        Map<String, Object> relMap = new HashMap<>();
        relMap.put("data", data);
        relMap.put("status", status);
        if (!StringUtil.isNullOrEmpty(confirm)) {
            relMap.put("confirmId", confirm);
        }
        return relMap;
    }

    /**
     * 返回包含成功Result的Map
     */
    public static Map<String, Object> success() {
        return success(null);
    }

    public static Map<String, Object> success(Object data) {
        return build(STATUS_SUCCESS, data);
    }

    /**
     * 返回包含失败Result的Map
     */
    public static Map<String, Object> fail(String message) {
        return fail(400, message);
    }

    /**
     * 返回包含失败Result的Map
     */
    public static Map<String, Object> fail(int code, String message) {
        return fail(code, "", message);
    }

    /**
     * 返回包含失败Result的Map
     */
    public static Map<String, Object> fail(int code, String type, String message) {
        return fail(new AjaxMsg(code, type, message));
    }

    /**
     * 返回包含失败Result的Map
     */
    public static Map<String, Object> fail(AjaxMsg ajaxMsg) {
        return build(STATUS_FAILED, ajaxMsg);
    }

    public static Map<String, Object> info(String tip) {
        return build(STATUS_SUCCESS, new AjaxMsg(0, COLOR_INFO, tip));
    }

    public static Map<String, Object> warning(String tip) {
        return build(STATUS_SUCCESS, new AjaxMsg(0, COLOR_WARNING, tip));
    }

    public static Map<String, Object> error(String tip) {
        return build(STATUS_SUCCESS, new AjaxMsg(0, COLOR_ERROR, tip));
    }

    private static Map<String, Object> confirm(String title, String message, List<String> key) {
        return build(STATUS_SUCCESS, key.toString(), ImmutableMap.of("title", title, "message", message));
    }

}

