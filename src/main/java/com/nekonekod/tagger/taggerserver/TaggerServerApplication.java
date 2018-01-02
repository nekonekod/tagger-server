package com.nekonekod.tagger.taggerserver;

import com.nekonekod.tagger.taggerserver.db.JsonDBHelper;
import com.nekonekod.tagger.taggerserver.entity.IllustCollection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaggerServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaggerServerApplication.class, args);
    }
}
