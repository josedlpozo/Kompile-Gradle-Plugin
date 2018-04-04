package com.josedlpozo.kompile.plugin

import com.josedlpozo.kompile.core.Kompile
import org.gradle.BuildListener
import org.gradle.BuildResult
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionGraph
import org.gradle.api.execution.TaskExecutionGraphListener
import org.gradle.api.execution.TaskExecutionListener
import org.gradle.api.initialization.Settings
import org.gradle.api.invocation.Gradle
import org.gradle.api.tasks.TaskState
import java.util.concurrent.TimeUnit

class KompileListener(private val project: Project) : TaskExecutionListener, BuildListener, TaskExecutionGraphListener {

    private lateinit var kompile: Kompile
    private val tasks: MutableMap<Task, Long> = mutableMapOf()
    private var start: Long = 0.toLong()

    override fun beforeExecute(task: Task?) {
        task?.let {
            tasks[it] = System.nanoTime()
        }
    }

    override fun graphPopulated(p0: TaskExecutionGraph?) {
        start = System.nanoTime()
    }

    override fun afterExecute(task: Task?, state: TaskState?) {
        if (task == null) return
        val savedTime = tasks[task] ?: return
        tasks[task] = System.nanoTime() - savedTime
    }

    override fun settingsEvaluated(settings: Settings?) {}

    override fun buildFinished(result: BuildResult?) {
        if (result?.failure != null) return

        val finish = System.nanoTime()

        val totalTasks = tasks.values.fold(0.toLong(), { acc, value -> acc + value })

        val seconds = TimeUnit.SECONDS.convert(totalTasks, TimeUnit.NANOSECONDS)

        val parameters = project.extensions.getByName(KompileExtension.NAME) as KompileExtension
        kompile = Kompile(parameters.verbose, parameters.host)
        kompile.handle(seconds)
    }

    override fun projectsLoaded(gradle: Gradle?) {}

    override fun buildStarted(gradle: Gradle?) {}

    override fun projectsEvaluated(gradle: Gradle?) {}
}