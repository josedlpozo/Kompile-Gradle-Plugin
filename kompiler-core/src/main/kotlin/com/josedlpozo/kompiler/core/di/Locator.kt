package com.josedlpozo.kompiler.core.di

import com.josedlpozo.kompiler.core.api.ApiClient
import com.josedlpozo.kompiler.core.command.CommandExecutor
import com.josedlpozo.kompiler.core.logger.Logger

interface Locator {

    val logger : Logger

    val apiClient : ApiClient

    val commandExecutor : CommandExecutor

}