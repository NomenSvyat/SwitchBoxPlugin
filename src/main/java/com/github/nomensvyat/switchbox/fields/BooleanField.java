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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BooleanField that = (BooleanField) o;

        return b == that.b;
    }

    @Override
    public int hashCode() {
        return (b ? 1 : 0);
    }
}
