package com.nekonekod.tagger.taggerserver;

import com.nekonekod.tagger.taggerserver.db.JsonDBHelper;
import com.nekonekod.tagger.taggerserver.entity.IllustCollection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TaggerServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaggerServerApplication.class, args);
    }
}
