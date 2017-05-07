package com.github.nomensvyat.switchbox;

import com.github.nomensvyat.switchbox.fields.Field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FieldMap {
    private final Map<String, List<Field>> map = new HashMap<>();

    public FieldMap() {
    }

    public void add(String fieldContainerName, Field field) {
        List<Field> fields = map.computeIfAbsent(fieldContainerName, k -> new ArrayList<>());
        fields.add(field);
    }
}
