package com.nekonekod.tagger.taggerserver.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @author duwenjun
 * @date 2018/1/9
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TagServiceTest {

    @Resource
    TagService tagService;

    @Test
    public void ignoreTags() throws Exception {
        tagService.ignoreTags().forEach(System.out::println);
        tagService.ignoreTags().forEach(System.out::println);
    }

    @Test
    public void reMapTags() throws Exception {
        tagService.reMapTags().forEach((k,v)-> System.out.println(k + " -> " + v));
        tagService.reMapTags().forEach((k,v)-> System.out.println(k + " -> " + v));
    }

}