package com.github.nomensvyat.switchbox.fields;

public final class LongField implements UnnamedField {

    private final long value;

    public LongField(long value) {
        this.value = value;
    }

    @Override
    public String getType() {
        return "long";
    }

    @Override
    public String getValue() {
        return Long.toString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LongField longField = (LongField) o;

        return value == longField.value;
    }

    @Override
    public int hashCode() {
        return (int) (value ^ (value >>> 32));
    }
}
