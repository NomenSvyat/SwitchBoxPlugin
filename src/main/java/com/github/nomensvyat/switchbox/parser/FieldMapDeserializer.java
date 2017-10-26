package com.github.nomensvyat.switchbox.parser;

import com.github.nomensvyat.switchbox.FieldMap;
import com.github.nomensvyat.switchbox.fields.Field;
import com.github.nomensvyat.switchbox.fields.NamedField;
import com.github.nomensvyat.switchbox.fields.UnnamedField;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

class FieldMapDeserializer implements JsonDeserializer<FieldMap> {
    @Override
    public FieldMap deserialize(JsonElement json,
                                Type typeOfT,
                                JsonDeserializationContext context) throws JsonParseException {
        JsonObject rootObject = json.getAsJsonObject();

        Set<Map.Entry<String, JsonElement>> entrySet = rootObject.entrySet();

        FieldMap fieldMap = new FieldMap();
        for (Map.Entry<String, JsonElement> entry : entrySet) {
            JsonObject propertyObject = entry.getValue().getAsJsonObject();
            String propertyName = entry.getKey();

            parsePropertyObject(propertyObject, propertyName, fieldMap, context);
        }

        return fieldMap;
    }

    private void parsePropertyObject(JsonObject propertyObject,
                                     String propertyName,
                                     FieldMap fieldMap,
                                     JsonDeserializationContext context) {
        Set<Map.Entry<String, JsonElement>> entrySet = propertyObject.entrySet();

        for (Map.Entry<String, JsonElement> entry : entrySet) {
            String fieldContainerName = entry.getKey();
            JsonElement value = entry.getValue();

            UnnamedField unnamedField = context.deserialize(value, UnnamedField.class);
            Field field = new NamedField(unnamedField, propertyName);

            fieldMap.put(fieldContainerName, field);
        }
    }
}
