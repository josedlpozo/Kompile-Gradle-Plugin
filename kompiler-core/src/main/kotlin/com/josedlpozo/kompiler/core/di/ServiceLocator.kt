package com.josedlpozo.kompiler.core.di

import com.josedlpozo.kompiler.core.api.ApiClient
import com.josedlpozo.kompiler.core.api.KompilerApiClient
import com.josedlpozo.kompiler.core.command.CommandExecutor
import com.josedlpozo.kompiler.core.logger.Logger

class ServiceLocator(private val verbose: Boolean, private val endPoint: String) : Locator {

    override val logger: Logger by lazy { Logger(verbose) }

    override val apiClient: ApiClient by lazy { KompilerApiClient(endPoint, logger) }

    override val commandExecutor: CommandExecutor by lazy { CommandExecutor() }

}