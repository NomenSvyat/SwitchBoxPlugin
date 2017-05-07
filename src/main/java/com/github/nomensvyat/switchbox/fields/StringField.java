package com.github.nomensvyat.switchbox.fields;

public final class StringField implements UnnamedField {
    private final String value;

    public StringField(String value) {
        this.value = value;
    }

    @Override
    public String getType() {
        return "String";
    }

    @Override
    public String getValue() {
        return "\" " + value + "\"";
    }
}
