package com.github.nomensvyat.switchbox.fields;

public final class BooleanField implements UnnamedField {
    private final boolean b;

    public BooleanField(boolean b) {
        this.b = b;
    }

    @Override
    public String getType() {
        return "boolean";
    }

    @Override
    public String getValue() {
        return Boolean.toString(b);
    }
}
