package com.josedlpozo.kompile.core

import arrow.core.None
import arrow.syntax.option.some
import com.josedlpozo.kompile.core.api.ApiClient
import com.josedlpozo.kompile.core.command.CommandExecutor
import com.josedlpozo.kompile.core.command.CommandLineExecutor.Companion.PROJECT_NAME_COMMAND
import com.josedlpozo.kompile.core.command.CommandLineExecutor.Companion.USER_ALIAS_COMMAND
import com.josedlpozo.kompile.core.command.CommandLineExecutor.Companion.USER_EMAIL_COMMAND
import com.josedlpozo.kompile.core.di.Locator
import com.josedlpozo.kompile.core.models.Kompile
import com.nhaarman.mockito_kotlin.*
import org.junit.Test

class KompilerTest {

    private lateinit var kompiler: Kompiler
    private val commandExecutor: CommandExecutor = mock {  }
    private val apiClient: ApiClient = mock {  }
    private val serviceLocator: Locator = mock {
        on { commandExecutor } doReturn commandExecutor
        on { apiClient } doReturn apiClient
    }

    @Test fun `given an empty host, then saveKompile is not called`() {
        kompiler = Kompiler("", serviceLocator)

        kompiler.handle(200)

        verify(apiClient, never()).saveKompile(any())
    }

    @Test fun `given a zero duration, then saveKompile is not called`() {
        kompiler = Kompiler(HOST, serviceLocator)

        kompiler.handle(0)

        verify(apiClient, never()).saveKompile(any())
    }

    @Test fun `given no user name in git project, then saveKompile is not called`() {
        whenever(commandExecutor.execute(USER_ALIAS_COMMAND)).thenReturn(None)
        whenever(commandExecutor.execute(USER_EMAIL_COMMAND)).thenReturn(USER_EMAIL.some())
        whenever(commandExecutor.execute(PROJECT_NAME_COMMAND)).thenReturn(PROJECT.some())

        kompiler = Kompiler(HOST, serviceLocator)

        kompiler.handle(DURATION)

        verify(apiClient, never()).saveKompile(any())
    }

    @Test fun `given no user email in git project, then saveKompile is not called`() {
        whenever(commandExecutor.execute(USER_ALIAS_COMMAND)).thenReturn(USER_ALIAS.some())
        whenever(commandExecutor.execute(USER_EMAIL_COMMAND)).thenReturn(None)
        whenever(commandExecutor.execute(PROJECT_NAME_COMMAND)).thenReturn(PROJECT.some())

        kompiler = Kompiler(HOST, serviceLocator)

        kompiler.handle(DURATION)

        verify(apiClient, never()).saveKompile(any())
    }

    @Test fun `given no project in git project, then saveKompile is not called`() {
        whenever(commandExecutor.execute(USER_ALIAS_COMMAND)).thenReturn(USER_ALIAS.some())
        whenever(commandExecutor.execute(USER_EMAIL_COMMAND)).thenReturn(USER_EMAIL.some())
        whenever(commandExecutor.execute(PROJECT_NAME_COMMAND)).thenReturn(None)

        kompiler = Kompiler(HOST, serviceLocator)

        kompiler.handle(DURATION)

        verify(apiClient, never()).saveKompile(any())
    }

    @Test fun `given host not empty, duration not zero, user email and project, then saveKompile is called`() {
        whenever(commandExecutor.execute(USER_ALIAS_COMMAND)).thenReturn(USER_ALIAS.some())
        whenever(commandExecutor.execute(USER_EMAIL_COMMAND)).thenReturn(USER_EMAIL.some())
        whenever(commandExecutor.execute(PROJECT_NAME_COMMAND)).thenReturn(PROJECT.some())

        kompiler = Kompiler(HOST, serviceLocator)

        kompiler.handle(DURATION)

        verify(apiClient).saveKompile(Kompile(DURATION, USER_EMAIL, PROJECT, USER_ALIAS))
    }

    companion object {
        const val HOST = "http://kompiler.io"
        const val DURATION = 7000L

        const val USER_ALIAS = "kompiler-user"
        const val USER_EMAIL = "kompiler@info.com"
        const val PROJECT = "Kompiler-plugin"
    }
}