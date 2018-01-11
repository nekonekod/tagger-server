package com.nekonekod.tagger.taggerserver.util;

import org.junit.Ignore;
import org.junit.Test;

import java.nio.file.Paths;

/**
 * @author duwenjun
 * @date 2018/1/10
 */
@Ignore
public class JpHashUtilTest {

    @Test
    public void getHash() {
//        String jpg = JpHashUtil.getHash(Paths.get("/Users/nekod/Downloads/pics/201801.jpg").toFile());
//        System.out.println("jpg:" + jpg);
        String png = JpHashUtil.getHash(Paths.get("/Users/nekod/Downloads/pics/63547033_p0.png").toFile());
        System.out.println("png:" + png);

    }


    @Test
    public void getSimilarity() throws Exception {
        String cat = JpHashUtil.getHash(Paths.get("/Users/nekod/Downloads/pics/cat.jpeg").toFile());
        String smallCat = JpHashUtil.getHash(Paths.get("/Users/nekod/Downloads/pics/cat_small.png").toFile());
        String xsCat = JpHashUtil.getHash(Paths.get("/Users/nekod/Downloads/pics/cat_xs.png").toFile());
        String rotateCat = JpHashUtil.getHash(Paths.get("/Users/nekod/Downloads/pics/cat_rotate.jpeg").toFile());
        System.out.println("smallCat:" + JpHashUtil.getSimilarity(cat, smallCat));
        System.out.println("xsCat:" + JpHashUtil.getSimilarity(cat, xsCat));
        System.out.println("rotateCat:" + JpHashUtil.getSimilarity(cat, rotateCat));
    }

}