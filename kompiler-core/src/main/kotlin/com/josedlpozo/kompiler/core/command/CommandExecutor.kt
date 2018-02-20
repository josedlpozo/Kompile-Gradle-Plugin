package com.josedlpozo.kompiler.core.command

import arrow.core.Option
import arrow.data.Try
import java.util.*

class CommandExecutor {

    companion object {
        const val PROJECT_NAME_COMMAND = "git config --get remote.origin.url | sed 's/.*\\///' | sed 's/\\.git//'"
        const val USER_EMAIL_COMMAND = "git config --get user.email"
        const val USER_ALIAS_COMMAND = "git config --get user.name"
    }

    fun execute(command: String): Option<String> = Try {
        val execute = arrayOf("/bin/sh", "-c", command)
        val execution = Runtime.getRuntime().exec(execute)
        val scanner = Scanner(execution.inputStream)
        val output = scanner.nextLine()
        scanner.close()
        output
    }.toOption()
}