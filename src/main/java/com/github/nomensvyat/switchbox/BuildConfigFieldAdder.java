package com.github.nomensvyat.switchbox;

import com.github.nomensvyat.switchbox.fields.Field;

import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

import java.util.ArrayList;
import java.util.List;

public class BuildConfigFieldAdder {
    private final List<FieldContainer> fieldContainers;
    private final Logger log = Logging.getLogger(this.getClass());

    private BuildConfigFieldAdder(List<FieldContainer> fieldContainers) {
        this.fieldContainers = fieldContainers;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void add(final FieldMap fieldMap) {
        if (fieldMap == null) {
            return;
        }
        log.info("Adding build config fields");

        fieldContainers.forEach(fieldContainer -> {
            List<Field> fieldList = fieldMap.get(fieldContainer.getName());
            if (fieldList != null) {
                fieldList.forEach(fieldContainer::add);
            }
        });
    }

    public void remove(final FieldMap fieldMap) {
        if (fieldMap == null) {
            return;
        }
        log.info("Removing build config fields");

        fieldContainers.forEach(fieldContainer -> {
            List<Field> fieldList = fieldMap.get(fieldContainer.getName());
            if (fieldList != null) {
                fieldList.forEach(fieldContainer::remove);
            }
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
