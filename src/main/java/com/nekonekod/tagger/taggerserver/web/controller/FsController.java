package com.nekonekod.tagger.taggerserver.web.controller;

import com.nekonekod.tagger.taggerserver.dto.PathDto;
import com.nekonekod.tagger.taggerserver.util.AjaxResultUtil;
import com.nekonekod.tagger.taggerserver.util.FsWatcher;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;

/**
 * @author duwenjun
 * @date 2018/1/10
 */
@RestController
@RequestMapping("fs")
public class FsController {

    @Resource
    private FsWatcher fsWatcher;

    @RequestMapping("watch/register")
    public Object register(@RequestBody PathDto pathDto) {
        fsWatcher.registerDir(new File(pathDto.getPath()));
        return AjaxResultUtil.success(fsWatcher.watchedDir());
    }

    @RequestMapping("watch/unregister")
    public Object unregister(@RequestBody PathDto pathDto) {
        fsWatcher.unregisterDir(new File(pathDto.getPath()));
        return AjaxResultUtil.success(fsWatcher.watchedDir());
    }
}
