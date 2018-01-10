package com.nekonekod.tagger.taggerserver.web.controller;

import com.nekonekod.tagger.taggerserver.dto.PathDto;
import com.nekonekod.tagger.taggerserver.util.AjaxResultUtil;
import com.nekonekod.tagger.taggerserver.util.FsWatcher;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

/**
 * @author duwenjun
 * @date 2018/1/10
 */
@RestController
@RequestMapping("fs")
public class FsController {

    private FsWatcher fsWatcher;

    public FsController() {
        this.fsWatcher = FsWatcher.getInstance();
    }

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
