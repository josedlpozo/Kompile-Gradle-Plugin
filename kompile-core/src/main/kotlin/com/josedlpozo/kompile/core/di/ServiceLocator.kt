package com.josedlpozo.kompile.core.di

import com.josedlpozo.kompile.core.api.ApiClient
import com.josedlpozo.kompile.core.api.KompilerApiClient
import com.josedlpozo.kompile.core.command.CommandExecutor
import com.josedlpozo.kompile.core.command.CommandLineExecutor
import com.josedlpozo.kompile.core.logger.Logger

class ServiceLocator(private val verbose: Boolean, private val endPoint: String) : Locator {

    override val logger: Logger by lazy { Logger(verbose) }

    override val apiClient: ApiClient by lazy { KompilerApiClient(endPoint, logger) }

    override val commandExecutor: CommandExecutor by lazy { CommandLineExecutor() }

}