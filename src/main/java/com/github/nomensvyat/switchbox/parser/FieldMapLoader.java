package com.github.nomensvyat.switchbox.parser;


import com.github.nomensvyat.switchbox.FieldMap;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import org.gradle.api.tasks.StopExecutionException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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

        try (FileReader fileReader = new FileReader(file)) {
            cachedFieldMap = gson.fromJson(fileReader, FieldMap.class);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("File not found", e);
        } catch (IOException e) {
            throw new IllegalStateException("Can't read file", e);
        } catch (JsonParseException e) {
            StopExecutionException stopExecutionException = new StopExecutionException(
                    "Can't parse fields from file: " + file.getAbsolutePath());
            stopExecutionException.initCause(e);
            throw stopExecutionException;
        }

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
