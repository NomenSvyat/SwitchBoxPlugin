package com.github.nomensvyat.switchbox;

import com.github.nomensvyat.switchbox.fields.Field;

import java.util.*;

public class FieldMap {
    private final Map<String, List<Field>> map = new HashMap<>();

    public FieldMap() {
    }

    public void add(String fieldContainerName, Field field) {
        List<Field> fields = map.computeIfAbsent(fieldContainerName, k -> new ArrayList<>());
        fields.add(field);
    }

    public List<Field> get(String fieldContainerName) {
        List<Field> result = map.get(fieldContainerName);
        if (result == null) {
            result = Collections.emptyList();
        }
        return result;
    }

    @Override
    public String toString() {
        return "FieldMap{" +
                "map=" + map +
                '}';
    }
}
