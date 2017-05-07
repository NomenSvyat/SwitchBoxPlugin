package com.github.nomensvyat.switchbox;

import com.android.build.gradle.internal.dsl.BuildType;
import com.android.build.gradle.internal.dsl.ProductFlavor;

class FieldContainerFabric {

    public static FieldContainer create(ProductFlavor productFlavor) {
        return field -> productFlavor.buildConfigField(field.getType(), field.getName(), field.getValue());
    }

    public static FieldContainer create(BuildType buildType) {
        return field -> buildType.buildConfigField(field.getType(), field.getName(), field.getValue());
    }
}
