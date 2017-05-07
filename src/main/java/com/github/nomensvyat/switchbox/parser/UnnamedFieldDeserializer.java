package com.github.nomensvyat.switchbox.parser;

import com.github.nomensvyat.switchbox.fields.FieldFabric;
import com.github.nomensvyat.switchbox.fields.UnnamedField;
import com.google.gson.*;

import java.lang.reflect.Type;

class UnnamedFieldDeserializer implements JsonDeserializer<UnnamedField> {

    @Override
    public UnnamedField deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (!json.isJsonPrimitive()) {
            throw new JsonParseException("Is not a primitive: " + json.toString());
        }

        JsonPrimitive jsonPrimitive = json.getAsJsonPrimitive();

        UnnamedField result = null;

        if (jsonPrimitive.isBoolean()) {
            result = FieldFabric.create(jsonPrimitive.getAsBoolean());
        } else if (jsonPrimitive.isString()) {
            result = FieldFabric.create(jsonPrimitive.getAsString());
        } else if (jsonPrimitive.isNumber()) {
            // TODO: 07.05.2017 Find out how to figure out which type field is
            double asDouble = jsonPrimitive.getAsDouble();
            long asLong = jsonPrimitive.getAsLong();
        }


        if (result == null) {
            throw new JsonParseException("JsonElement is of unsupported type");
        }

        return result;
    }
}
