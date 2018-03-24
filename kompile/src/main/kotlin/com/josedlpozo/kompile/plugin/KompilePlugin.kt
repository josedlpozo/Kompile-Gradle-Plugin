package com.josedlpozo.kompile.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class KompilePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.extensions.create(KompileExtension.NAME, KompileExtension::class.java)
        addListener(project)
    }

    private fun addListener(project: Project) {
        val listener = KompileListener(project)
        project.gradle.addListener(listener)
        project.gradle.addBuildListener(listener)
    }

}
