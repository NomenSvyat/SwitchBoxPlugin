package com.github.nomensvyat.switchbox

import com.android.build.gradle.*
import com.github.nomensvyat.switchbox.parser.FieldMapLoader
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.tasks.StopExecutionException

class SwitchBoxPlugin implements Plugin<Project> {
    private static final String TASK_NAME = "syncSwitchBox"
    private static final String EXTENSION_NAME = "switchBox"

    private final Logger log = Logging.getLogger SwitchBoxPlugin
    private SwitchBoxExtension switchBoxExtension

    @Override
    void apply(Project project) {
        if (!hasAndroidPlugin(project)) {
            throw new StopExecutionException("Must be applied after 'android' or 'android-library' plugin.")
        }

        switchBoxExtension = project.extensions.create(EXTENSION_NAME, SwitchBoxExtension)

        project.afterEvaluate {
            doAfterEvaluate(project)
        }
    }

    def doAfterEvaluate(Project project) {
        def syncTask = project.task(TASK_NAME)

        syncTask.doFirst { loadFields(project) }

        syncTask.onlyIf { switchBoxExtension.filePath }

        if (switchBoxExtension.syncOnBuild) {
            project.tasks.all { task ->
                if (task.name.contains("BuildConfig")) {
                    task.dependsOn syncTask
                }
            }
        } else {
            //done only on gradle sync
            loadFields(project)
        }
    }

    private void loadFields(Project project) {
        if (switchBoxExtension.filePath.isEmpty()) {
            return
        }

        File file = getFile(project, switchBoxExtension.filePath)

        log.info("Starting SwitchBox")

        BuildConfigFieldAdder buildConfigFieldAdder = createBuildConfigAdder(getAndroidExtension(project))

        def cachedFieldMap = FieldMapLoader.getCached()
        if (cachedFieldMap != null) {
            buildConfigFieldAdder.remove(cachedFieldMap)
        }

        def fieldMap = FieldMapLoader.load(file)

        buildConfigFieldAdder.add(fieldMap)
    }

    private BuildConfigFieldAdder createBuildConfigAdder(BaseExtension androidExtension) {
        BuildConfigFieldAdder.Builder builder = BuildConfigFieldAdder.builder()
        builder.addFieldContainer(FieldContainerFabric.createDefault(androidExtension.defaultConfig))
        androidExtension.buildTypes.all {
            builder.addFieldContainer(FieldContainerFabric.create(it))
        }
        androidExtension.productFlavors.all {
            builder.addFieldContainer(FieldContainerFabric.create(it))
        }

        def buildConfigFieldAdder = builder.build()
        buildConfigFieldAdder
    }

    BaseExtension getAndroidExtension(Project project) {
        return project.extensions.findByType(AppExtension) ?: project.extensions.getByType(LibraryExtension)
    }

    static def getFile(Project project, String filePath) {
        try {
            return project.file(filePath)
        } catch (Exception e) {
            throw new Exception("File '${filePath}' not found", e)
        }
    }

    static def hasAndroidPlugin(Project project) {
        return project.plugins.hasPlugin(AppPlugin) || project.plugins.hasPlugin(LibraryPlugin)
    }
}
