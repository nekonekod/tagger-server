package com.nekonekod.tagger.taggerserver.service;

import com.nekonekod.tagger.taggerserver.entity.IllustCollection;

import java.io.File;
import java.util.List;

/**
 * deal with pixiv source
 *
 * @author duwenjun
 * @date 2017/12/29
 */
public interface PixivService {

    List<IllustCollection> parseRawData(String json);

    /**
     * rename image files:
     * 111_p.jpg -> 111.jpg
     * 111_p0.jpg -> no change
     * 111.jpg -> no change
     * suffix:{jpg,png}
     *
     * @param dir    root dir (with recursion)
     * @param dupDir if duplicated , move file to {dupDir}
     */
    void renamePixivImageFiles(File dir, File dupDir);

}
