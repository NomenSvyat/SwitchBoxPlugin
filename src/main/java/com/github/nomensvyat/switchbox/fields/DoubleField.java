package com.github.nomensvyat.switchbox.fields;

public final class DoubleField implements UnnamedField {

    private final double value;

    public DoubleField(double value) {
        this.value = value;
    }

    @Override
    public String getType() {
        return "double";
    }

    @Override
    public String getValue() {
        return Double.toString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DoubleField that = (DoubleField) o;

        return Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(value);
        return (int) (temp ^ (temp >>> 32));
    }
}
