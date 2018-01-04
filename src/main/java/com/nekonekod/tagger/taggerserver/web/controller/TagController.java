package com.nekonekod.tagger.taggerserver.web.controller;

import com.nekonekod.tagger.taggerserver.service.TagService;
import com.nekonekod.tagger.taggerserver.util.AjaxResultUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * tag api
 *
 * @author duwenjun
 * @date 2018/1/3
 */
@RestController
@RequestMapping("tag")
public class TagController {

    @Resource
    private TagService tagService;

    @RequestMapping("ignored")
    public Object ignored() {
        return AjaxResultUtil.success(tagService.ignoreTags());
    }

    @RequestMapping("reMap")
    public Object reMap() {
        return AjaxResultUtil.success(tagService.reMapTags());
    }

}
