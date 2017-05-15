package com.github.nomensvyat.switchbox.parser;


import com.github.nomensvyat.switchbox.FieldMap;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class FieldMapLoader {

    private final Gson gson;
    private FieldMap cachedFieldMap;
    private long cacheTimestamp;

    private FieldMapLoader() {
        gson = GsonFabric.create();
    }

    private static FieldMapLoader getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public static FieldMap load(File file) {
        return getInstance().loadInternal(file);
    }

    public static boolean needToLoad(File file) {
        return getInstance().needToLoadInternal(file);
    }

    private boolean needToLoadInternal(File file) {
        return !(cacheTimestamp == file.lastModified() && cachedFieldMap != null);
    }

    private FieldMap loadInternal(File file) {
        checkFile(file);

        FileReader fileReader;
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File not found", e);
        }

        cachedFieldMap = gson.fromJson(fileReader, FieldMap.class);
        cacheTimestamp = file.lastModified();

        return cachedFieldMap;
    }

    private void checkFile(File file) {
        if (!file.exists()) {
            throw new IllegalArgumentException("File " + file.getAbsolutePath() + " not found");
        }
    }

    private static class InstanceHolder {
        private static FieldMapLoader INSTANCE = new FieldMapLoader();
    }
}
