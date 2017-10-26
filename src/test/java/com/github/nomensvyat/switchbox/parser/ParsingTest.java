package com.github.nomensvyat.switchbox.parser;

import com.github.nomensvyat.switchbox.FieldMap;
import com.github.nomensvyat.switchbox.fields.Field;
import com.google.gson.Gson;

import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ParsingTest {
    private Map<String, String> propertiesMap = new HashMap<>();

    {
        propertiesMap.put("prop1", "true");
        propertiesMap.put("prop2", "\"def\"");
        propertiesMap.put("prop3", "1");
    }

    @Test
    public void testParsing() {
        InputStream legalStream = getClass().getClassLoader()
                .getResourceAsStream("legal.json");

        Gson gson = GsonFabric.create();

        FieldMap fieldMap = gson.fromJson(new InputStreamReader(legalStream), FieldMap.class);

        assertNotNull(fieldMap);

        List<Field> defaultFieldList = fieldMap.get("default");

        assertNotNull(defaultFieldList);
        assertEquals(false, defaultFieldList.isEmpty());

        defaultFieldList.forEach(field -> {
            assertEquals(propertiesMap.get(field.getName()), field.getValue());
        });
    }
}
