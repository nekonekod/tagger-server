package com.nekonekod.tagger.taggerserver.util;

import com.pragone.jphash.image.radial.RadialHash;
import com.pragone.jphash.jpHash;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;

/**
 * @author duwenjun
 * @date 2018/1/10
 */
@Log4j2
public class JpHashUtil {

    public static String getHash(File file) {
        RadialHash hash = null;
        try {
            hash = jpHash.getImageRadialHash(file);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return hash.toString();
    }

    public static double getSimilarity(String hash1, String hash2) {
        return jpHash.getSimilarity(
                RadialHash.fromString(hash1),
                RadialHash.fromString(hash2));
    }
}
