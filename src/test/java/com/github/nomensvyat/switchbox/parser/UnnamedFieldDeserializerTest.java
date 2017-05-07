package com.github.nomensvyat.switchbox.parser;

import com.github.nomensvyat.switchbox.fields.FieldFabric;
import com.github.nomensvyat.switchbox.fields.UnnamedField;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.intellij.lang.annotations.Language;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class UnnamedFieldDeserializerTest {
    @Language("JSON")
    private final String legalPropertyJson = "{\n" +
            "  \"String\": \"some string\",\n" +
            "  \"boolean\": false\n" +
            "}";

    @Test
    public void testDeserialization() {
        Gson gson = GsonFabric.create();

        JsonObject jsonObject = gson.fromJson(legalPropertyJson, JsonObject.class);

        List<UnnamedField> expectedEquals = new ArrayList<>();
        expectedEquals.add(FieldFabric.create("some string"));
        expectedEquals.add(FieldFabric.create(false));

        List<UnnamedField> expectedNotEquals = new ArrayList<>();
        expectedNotEquals.add(FieldFabric.create("some other string"));
        expectedNotEquals.add(FieldFabric.create(true));

        Iterator<UnnamedField> iterator = expectedEquals.iterator();
        Iterator<UnnamedField> iteratorNotEquals = expectedNotEquals.iterator();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String typeName = entry.getKey();
            JsonElement jsonElement = entry.getValue();

            UnnamedField unnamedField = gson.fromJson(jsonElement, UnnamedField.class);

            assertEquals("Parsed wrong field", iterator.next(), unnamedField);

            assertNotEquals("Parsed wrong field. Must not be equal", iteratorNotEquals.next(), unnamedField);
        }
    }

}