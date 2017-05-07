package com.github.nomensvyat.switchbox.fields;

public class FieldFabric {
    private FieldFabric() {
        throw new UnsupportedOperationException("Instances not allowed");
    }

    public static UnnamedField create(boolean value) {
        return new BooleanField(value);
    }

    public static UnnamedField create(String value) {
        return new StringField(value);
    }
}
