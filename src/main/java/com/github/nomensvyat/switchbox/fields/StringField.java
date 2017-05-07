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
        return "\"" + value + "\"";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringField that = (StringField) o;

        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
