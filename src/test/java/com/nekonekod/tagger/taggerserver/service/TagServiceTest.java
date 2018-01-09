package com.nekonekod.tagger.taggerserver.service;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author duwenjun
 * @date 2018/1/9
 */
@RunWith(SpringRunner.class)
@SpringBootTest
//@Ignore
public class TagServiceTest {

    @Resource
    TagService tagService;

    @Test
    public void ignoreTags() throws Exception {
        tagService.ignoreTags().forEach(System.out::println);
        tagService.ignoreTags().forEach(System.out::println);//from cache
    }

    @Test
    public void reMapTags() throws Exception {
        tagService.reMapTags().forEach((k, v) -> System.out.println(k + " -> " + v));
        tagService.reMapTags().forEach((k, v) -> System.out.println(k + " -> " + v));//from cache
    }

    @Test
    public void addMapTags() throws Exception {
        tagService.addReMap("少女前線", "少女前线");
    }

    @Test
    public void updateTags(){
        System.out.println(tagService.updateTagString("$女の子$$初音ミク$$バレンタイン$$黒タイツ$$なにこれ可愛い$$VOCALOID$$ダッフルコート$$VOCALOID1000users入り$"));
    }
}