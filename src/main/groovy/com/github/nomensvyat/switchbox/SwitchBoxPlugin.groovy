package com.github.nomensvyat.switchbox

import com.android.build.gradle.*
import com.github.nomensvyat.switchbox.parser.FieldMapLoader
import org.gradle.api.*
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
        def syncTask = project.task("syncSwitchBox")

        syncTask.doFirst { loadFields(project, it) }

        if (switchBoxExtension.syncOnBuild) {
            project.tasks.all { task ->
                if (task.name.contains("BuildConfig")) {
                    task.dependsOn syncTask
                }
            }
        } else {
            //done only on gradle sync
            loadFields(project, null)
        }
    }

    private void loadFields(Project project, @Nullable Task task) {
        def file = getFile(project, switchBoxExtension.filePath)

        task?.setDidWork(false)
        if (!FieldMapLoader.needToLoad(file)) {
            return
        }
        task?.setDidWork(true)

        BuildConfigFieldAdder buildConfigFieldAdder = createBuildConfigAdder(getAndroidExtension(project))

        FieldMap cachedFieldMap = FieldMapLoader.getCached()
        buildConfigFieldAdder.remove(cachedFieldMap)

        def fieldMap = FieldMapLoader.load(file)

        buildConfigFieldAdder.add(fieldMap)
    }

    private BuildConfigFieldAdder createBuildConfigAdder(BaseExtension androidExtension) {
        def builder = BuildConfigFieldAdder.builder()
        builder.addFieldContainer(FieldContainerFabric.createDefault(androidExtension.defaultConfig))
        androidExtension.buildTypes.all {
            builder.addFieldContainer(FieldContainerFabric.create(it.getBuildConfigFields()))
        }
        androidExtension.productFlavors.all {
            builder.addFieldContainer(FieldContainerFabric.create(it))
        }

        def buildConfigFieldAdder = builder.build()
        buildConfigFieldAdder
    }

    BaseExtension getAndroidExtension(Project project) {
        def androidExtension
        try {
            androidExtension = project.extensions.getByType(AppExtension)
        } catch (UnknownDomainObjectException e) {
            androidExtension = project.extensions.getByType(LibraryExtension)
        }

        return androidExtension
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
