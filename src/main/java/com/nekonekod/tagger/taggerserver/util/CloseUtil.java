package com.nekonekod.tagger.taggerserver.util;

import lombok.extern.log4j.Log4j2;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author duwenjun
 * @date 2017/12/29
 */
@Log4j2
public class CloseUtil {

    /**
     * 关闭
     *
     * @param closeables
     */
    public static void close(Closeable... closeables) {
        for (Closeable closeable : closeables) {
            if (closeable != null)
                try {
                    closeable.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
        }
    }

}
