package com.nekonekod.tagger.taggerserver;

import com.nekonekod.tagger.taggerserver.db.JsonDBHelper;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * @author duwenjun
 * @date 2018/1/2
 */
@Component
public class ApplicationHelper {

    @PostConstruct
    public void init() {
        Reflections reflections = new Reflections("com.nekonekod.tagger.taggerserver.entity", new SubTypesScanner(false));
        Set<Class<?>> collections = reflections.getSubTypesOf(Object.class);
        collections.forEach(JsonDBHelper::initCollection);
    }

}
