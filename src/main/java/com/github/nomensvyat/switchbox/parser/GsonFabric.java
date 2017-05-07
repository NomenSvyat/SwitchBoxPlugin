package com.github.nomensvyat.switchbox.parser;

import com.github.nomensvyat.switchbox.FieldMap;
import com.github.nomensvyat.switchbox.fields.UnnamedField;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonFabric {
    public static Gson create() {
        return new GsonBuilder()
                .registerTypeAdapter(FieldMap.class, new FieldMapDeserializer())
                .registerTypeAdapter(UnnamedField.class, new UnnamedFieldDeserializer())
                .create();
    }
}
