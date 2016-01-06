package com.tencent.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction

/**
 * The configuration properties.
 *
 * @author Sim Sun (sunsj1231@gmail.com)
 */
public class AndResGuardSchemaTask extends DefaultTask {
    def configuration

    AndResGuardSchemaTask() {
        description = 'Assemble Proguard APK'
        group = 'andresguard'

        outputs.upToDateWhen { false }

        project.afterEvaluate {
            configuration = project.andResGuard
            configuration.targetDirectory = configuration.targetDirectory ?:
                    project.file("${project.buildDir}/generated-apk/ARG")

            if (project.plugins.hasPlugin('com.android.application')) {
                configureEnv()
            } else {
                throw new GradleException('generateARGApk: Android Application plugin required')
            }
            outputs.dir configuration.targetDirectory
        }
    }

    def configureEnv() {
        def android = project.extensions.android
        android.sourceSets.main.java.srcDirs += [ configuration.targetDirectory ]
    }

    @TaskAction
    def generate() {
        println "Using this configuration:\n $configuration"
    }
}