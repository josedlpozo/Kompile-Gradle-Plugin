package com.josedlpozo.kompiler.plugin

import com.josedlpozo.kompiler.core.Kompiler
import org.gradle.BuildListener
import org.gradle.BuildResult
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionListener
import org.gradle.api.initialization.Settings
import org.gradle.api.invocation.Gradle
import org.gradle.api.tasks.TaskState
import java.util.concurrent.TimeUnit

class KompilerListener(private val project: Project) : TaskExecutionListener, BuildListener {

    private lateinit var kompiler: Kompiler
    private val tasks: MutableMap<Task, Long> = mutableMapOf()
    private var start: Long = 0.toLong()

    override fun beforeExecute(task: Task?) {
        task?.let {
            tasks[it] = System.nanoTime()
        }
    }

    override fun afterExecute(task: Task?, state: TaskState?) {
        if (task == null) return
        val savedTime = tasks[task] ?: return
        tasks[task] = System.nanoTime() - savedTime
    }

    override fun settingsEvaluated(settings: Settings?) {}

    override fun buildFinished(result: BuildResult?) {
        val totalTasks = tasks.values.fold(0.toLong(), { acc, value -> acc + value })

        val seconds = TimeUnit.SECONDS.convert(totalTasks, TimeUnit.NANOSECONDS)

        val parameters = project.extensions.getByName(KompilerExtension.NAME) as KompilerExtension
        kompiler = Kompiler(parameters.verbose, parameters.endPoint)
        kompiler.handle(seconds)
    }

    override fun projectsLoaded(gradle: Gradle?) {
    }

    override fun buildStarted(gradle: Gradle?) {
        start = System.nanoTime()
    }

    override fun projectsEvaluated(gradle: Gradle?) {}
}