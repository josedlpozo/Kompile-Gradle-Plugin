package com.josedlpozo.kompile.core.di

import com.josedlpozo.kompile.core.api.ApiClient
import com.josedlpozo.kompile.core.command.CommandExecutor
import com.josedlpozo.kompile.core.logger.Logger

interface Locator {

    val logger : Logger

    val apiClient : ApiClient

    val commandExecutor : CommandExecutor

}