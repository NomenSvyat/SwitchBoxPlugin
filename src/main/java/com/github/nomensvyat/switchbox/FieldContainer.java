package com.github.nomensvyat.switchbox;

import com.github.nomensvyat.switchbox.fields.Field;

public interface FieldContainer {
    void add(Field field);

    void remove(Field field);

    String getName();

    String getGroup();
}
