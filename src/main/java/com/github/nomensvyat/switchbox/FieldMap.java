package com.github.nomensvyat.switchbox;

import com.github.nomensvyat.switchbox.fields.Field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FieldMap {
    /**
     * FieldContainer name to field map
     */
    private final Map<String, List<Field>> map = new HashMap<>();

    public FieldMap() {
    }

    public void put(String fieldContainerName, Field field) {
        List<Field> fields = map.computeIfAbsent(fieldContainerName, k -> new ArrayList<>());
        fields.add(field);
    }

    public List<Field> get(String fieldContainerName) {
        List<Field> result = map.get(fieldContainerName);
        if (result == null) {
            result = Collections.emptyList();
        }
        return Collections.unmodifiableList(result);
    }

    @Override
    public String toString() {
        return "FieldMap{" +
                "map=" + map +
                '}';
    }
}
