package com.nekonekod.tagger.taggerserver.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Objects;

/**
 * @author duwenjun
 * @date 2017/12/28
 */
public class FileUtil extends FileUtils {

    public static boolean isValidDir(File dir) {
        return Objects.nonNull(dir) && dir.exists() && dir.isDirectory();
    }

    public static boolean isEmptyDir(File file) {
        if (isValidDir(file)) return false;
        File[] files = file.listFiles();
        return files == null || file.length() == 0;
    }
}
