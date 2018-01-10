package com.nekonekod.tagger.taggerserver.web.controller;

import com.nekonekod.tagger.taggerserver.dto.TagDto;
import com.nekonekod.tagger.taggerserver.service.TagService;
import com.nekonekod.tagger.taggerserver.util.AjaxResultUtil;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
@RestController
@RequestMapping("tag")
public class TagController {

    @Resource
    private TagService tagService;

    @RequestMapping("saveIgnore")
    public Object addIgnore(@RequestBody TagDto param) {
        tagService.addIgnore(param.getName());
        return AjaxResultUtil.success("保存成功");
    }

    @RequestMapping("saveReMap")
    public Object addReMap(@RequestBody TagDto param) {
        tagService.addReMap(param.getName(), param.getMapTo());
        return AjaxResultUtil.success("保存成功");
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
    public Object remove(@RequestBody TagDto param) {
        tagService.remove(param.getName());
        return AjaxResultUtil.success("删除成功");
    }


}
