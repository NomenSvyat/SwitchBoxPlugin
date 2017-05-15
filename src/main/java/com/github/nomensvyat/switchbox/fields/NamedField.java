package com.github.nomensvyat.switchbox.fields;

public class NamedField implements Field {
    private final UnnamedField unnamedField;
    private final String name;

    public NamedField(UnnamedField unnamedField, String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name can't be null or empty");
        }
        if (unnamedField == null) {
            throw new NullPointerException("Unnamed field is null");
        }
        this.unnamedField = unnamedField;
        this.name = name;
    }

    @Override
    public String getType() {
        return unnamedField.getType();
    }

    @Override
    public String getValue() {
        return unnamedField.getValue();
    }

    @Override
    public String getName() {
        return name;
    }
}
