package com.nekonekod.tagger.taggerserver.web.controller;

import com.nekonekod.tagger.taggerserver.util.AjaxResultUtil;
import com.nekonekod.tagger.taggerserver.util.FsWatcher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author duwenjun
 * @date 2018/1/10
 */
@RestController
public class IndexController {

    @Resource
    private FsWatcher fsWatcher;

    @RequestMapping("alive")
    public Object register() {
        return AjaxResultUtil.success();
    }

}
