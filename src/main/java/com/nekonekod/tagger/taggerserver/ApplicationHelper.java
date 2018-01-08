package com.nekonekod.tagger.taggerserver;

import com.nekonekod.tagger.taggerserver.db.JsonDBHelper;
import io.jsondb.annotation.Document;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * @author duwenjun
 * @date 2018/1/2
 */
@Component
public class ApplicationHelper {

    @PostConstruct
    public void init() {
        initJsonDb();
    }

    private void initJsonDb() {
        Reflections reflections = new Reflections("com.nekonekod.tagger.taggerserver.entity", new SubTypesScanner(false));
        reflections.getSubTypesOf(Object.class)
                .stream()
                .filter(cls -> Objects.nonNull(cls.getDeclaredAnnotation(Document.class)))
                .forEach(JsonDBHelper::initCollection);
    }

}
