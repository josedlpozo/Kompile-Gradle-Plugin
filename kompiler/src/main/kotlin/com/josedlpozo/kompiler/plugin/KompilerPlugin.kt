package com.josedlpozo.kompiler.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class KompilerPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.extensions.create(KompilerExtension.NAME, KompilerExtension::class.java)
        addListener(project)
    }

    private fun addListener(project: Project) {
        val listener = KompilerListener(project)
        project.gradle.addListener(listener)
        project.gradle.addBuildListener(listener)
    }

}
