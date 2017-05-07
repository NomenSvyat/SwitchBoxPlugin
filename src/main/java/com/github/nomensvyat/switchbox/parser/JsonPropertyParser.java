package com.github.nomensvyat.switchbox.parser;


import com.github.nomensvyat.switchbox.FieldMap;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class JsonPropertyParser {

    private final Gson gson;

    public JsonPropertyParser() {
        gson = GsonFabric.create();
    }

    public FieldMap parse(File file) {
        checkFile(file);
        FileReader fileReader;
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File not found", e);
        }

        return gson.fromJson(fileReader, FieldMap.class);
    }

    private void checkFile(File file) {
        if (!file.exists()) {
            throw new IllegalArgumentException("File " + file.getAbsolutePath() + " not found");
        }
    }
}
