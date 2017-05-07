package com.github.nomensvyat.switchbox;

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
