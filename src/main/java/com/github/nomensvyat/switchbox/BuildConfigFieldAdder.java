package com.github.nomensvyat.switchbox;

import com.github.nomensvyat.switchbox.fields.Field;

import java.util.ArrayList;
import java.util.List;

public class BuildConfigFieldAdder {
    private final List<FieldContainer> fieldContainers;

    private BuildConfigFieldAdder(List<FieldContainer> fieldContainers) {
        this.fieldContainers = fieldContainers;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void compute(final FieldMap fieldMap) {
        fieldContainers.forEach(fieldContainer -> {
            List<Field> fieldList = fieldMap.get(fieldContainer.getName());
            fieldList.forEach(fieldContainer::add);
        });
    }

    public static class Builder {
        private final List<FieldContainer> fieldContainers = new ArrayList<>();

        public Builder addFieldContainer(FieldContainer fieldContainer) {
            fieldContainers.add(fieldContainer);
            return this;
        }

        public BuildConfigFieldAdder build() {
            return new BuildConfigFieldAdder(fieldContainers);
        }
    }
}
