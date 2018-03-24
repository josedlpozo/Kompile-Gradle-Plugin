package com.josedlpozo.kompile.core

import arrow.core.Option
import arrow.core.monad
import arrow.syntax.functor.map
import arrow.syntax.option.some
import arrow.typeclasses.binding
import com.josedlpozo.kompile.core.command.CommandLineExecutor.Companion.PROJECT_NAME_COMMAND
import com.josedlpozo.kompile.core.command.CommandLineExecutor.Companion.USER_ALIAS_COMMAND
import com.josedlpozo.kompile.core.command.CommandLineExecutor.Companion.USER_EMAIL_COMMAND
import com.josedlpozo.kompile.core.di.Locator
import com.josedlpozo.kompile.core.di.ServiceLocator
import com.josedlpozo.kompile.core.models.KompileInfo

class Kompile internal constructor(private val host: String, private val locator: Locator) {

    constructor(verbose: Boolean, host: String) : this(host, ServiceLocator(verbose, host))

    fun handle(duration: Long) {
        if (host.isEmpty() || duration == 0L) {
            return
        }

        val apiClient = locator.apiClient
        val commandExecutor = locator.commandExecutor

        Option.monad().binding {
            val user = commandExecutor.execute(USER_EMAIL_COMMAND).bind()
            val project = commandExecutor.execute(PROJECT_NAME_COMMAND).bind()
            val alias = commandExecutor.execute(USER_ALIAS_COMMAND).bind()
            val someDuration = duration.some().bind()
            KompileInfo(someDuration, user, project, alias)
        }.map { apiClient.saveKompile(it) }
    }

}