package com.nekonekod.tagger.taggerserver.db;

import io.jsondb.JsonDBTemplate;

import java.io.File;
import java.nio.file.Paths;

/**
 * @author duwenjun
 * @date 2017/12/21
 */
public class JsonDBHelper {

    //Actual location on disk for database files, process should have read-write permissions to this folder
    private static String dbFilesLocation;

    //Java package name where POJO's are present
    private static String baseScanPackage = "com.nekonekod.tagger.taggerserver";

    private static JsonDBTemplate instance;

    static {
        File db = Paths.get("data", "db").toFile();
        db.getParentFile().mkdirs();
        dbFilesLocation = db.getAbsolutePath();
        instance = new JsonDBTemplate(dbFilesLocation, baseScanPackage);
    }

    public static void initCollection(Class cls) {
        if (!instance.collectionExists(cls))
            instance.createCollection(cls);
    }

    public static JsonDBTemplate getInstance() {
        return instance;
    }

}
