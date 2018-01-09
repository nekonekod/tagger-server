package com.nekonekod.tagger.taggerserver.web.controller;

import com.nekonekod.tagger.taggerserver.model.TagParam;
import com.nekonekod.tagger.taggerserver.service.TagService;
import com.nekonekod.tagger.taggerserver.util.AjaxResultUtil;
import org.springframework.web.bind.annotation.RequestBody;
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

    @RequestMapping("addIgnore")
    public Object addIgnore(@RequestBody TagParam param) {
        tagService.addIgnore(param.getName());
        return AjaxResultUtil.success("添加成功");
    }

    @RequestMapping("addReMap")
    public Object addReMap(@RequestBody TagParam param) {
        tagService.addReMap(param.getName(), param.getMapTo());
        return AjaxResultUtil.success("添加成功");
    }

    @RequestMapping("ignored")
    public Object ignored() {
        return AjaxResultUtil.success(tagService.ignoreTags());
    }

    @RequestMapping("reMap")
    public Object reMap() {
        return AjaxResultUtil.success(tagService.reMapTags());
    }

    @RequestMapping("remove")
    public Object remove(@RequestBody TagParam param) {
        tagService.remove(param.getName());
        return AjaxResultUtil.success("删除成功");
    }


}
