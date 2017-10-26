package com.github.nomensvyat.switchbox;

import com.android.build.gradle.internal.dsl.BuildType;
import com.android.build.gradle.internal.dsl.DefaultConfig;
import com.android.build.gradle.internal.dsl.ProductFlavor;
import com.android.builder.model.ClassField;
import com.github.nomensvyat.switchbox.fields.Field;

import java.util.Map;

class FieldContainerFabric {

    public static FieldContainer create(final ProductFlavor productFlavor) {
        return new FieldContainer() {
            @Override
            public void add(Field field) {
                productFlavor.buildConfigField(field.getType(), field.getName(), field.getValue());
            }

            @Override
            public void remove(Field field) {
                Map<String, ClassField> buildConfigFields = productFlavor.getBuildConfigFields();
                if (buildConfigFields != null) {
                    buildConfigFields.remove(field.getName());
                }
            }

            @Override
            public String getName() {
                return productFlavor.getName();
            }

            @Override
            public String getGroup() {
                return productFlavor.getDimension();
            }
        };
    }

    public static FieldContainer createDefault(DefaultConfig defaultConfig) {
        return new FieldContainer() {
            @Override
            public void add(Field field) {
                defaultConfig.buildConfigField(field.getType(), field.getName(), field.getValue());
            }

            @Override
            public void remove(Field field) {
                Map<String, ClassField> buildConfigFields = defaultConfig.getBuildConfigFields();
                if (buildConfigFields != null) {
                    buildConfigFields.remove(field.getName());
                }
            }

            @Override
            public String getName() {
                return "default";
            }

            @Override
            public String getGroup() {
                return "default";
            }
        };
    }

    public static FieldContainer create(final BuildType buildType) {
        return new FieldContainer() {
            @Override
            public void add(Field field) {
                buildType.buildConfigField(field.getType(), field.getName(), field.getValue());
            }

            @Override
            public void remove(Field field) {
                Map<String, ClassField> buildConfigFields = buildType.getBuildConfigFields();
                if (buildConfigFields != null) {
                    buildConfigFields.remove(field.getName());
                }
            }

            @Override
            public String getName() {
                return buildType.getName();
            }

            @Override
            public String getGroup() {
                return "buildType";
            }
        };
    }
}
