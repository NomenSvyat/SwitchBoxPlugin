package com.github.nomensvyat.switchbox

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import com.github.nomensvyat.switchbox.parser.JsonPropertyParser
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.tasks.StopExecutionException

class SwitchBoxPlugin implements Plugin<Project> {
    final Logger log = Logging.getLogger SwitchBoxPlugin
    private SwitchBoxExtension switchBoxExtension

    @Override
    void apply(Project project) {
        if (!hasAndroidPlugin(project)) {
            throw new StopExecutionException("Must be applied after 'android' or 'android-library' plugin.")
        }

        switchBoxExtension = project.extensions.create("switchBox", SwitchBoxExtension)

        project.afterEvaluate {
            doAfterEvaluate(project)
        }
    }

    def doAfterEvaluate(Project project) {
        loadFields(project)
        def syncTask = project.task("syncSwitchBox") {
            loadFields(project)
        }
        if (switchBoxExtension.syncOnBuild) {
            project.tasks.all { task ->
                if (task.name.contains("BuildConfig")) {
                    task.dependsOn syncTask
                }
            }
        }
    }

    private void loadFields(Project project) {
        def appExtension = project.extensions.getByType(AppExtension)

        def builder = BuildConfigFieldAdder.builder()
        builder.addFieldContainer(FieldContainerFabric.createDefault(appExtension.defaultConfig))
        appExtension.buildTypes.all {
            builder.addFieldContainer(FieldContainerFabric.create(it))
        }
        appExtension.productFlavors.all {
            builder.addFieldContainer(FieldContainerFabric.create(it))
        }

        def buildConfigFieldAdder = builder.build()

        def file = getFile(project, switchBoxExtension.filePath)

        def fieldMap = new JsonPropertyParser().parse(file)

        buildConfigFieldAdder.compute(fieldMap)
    }

    static def getFile(Project project, String filePath) {
        try {
            return project.file(filePath)
        } catch (Exception e) {
            throw new Exception("File not found", e)
        }
    }

    static def hasAndroidPlugin(Project project) {
        return project.plugins.hasPlugin(AppPlugin) || project.plugins.hasPlugin(LibraryPlugin)
    }
}
