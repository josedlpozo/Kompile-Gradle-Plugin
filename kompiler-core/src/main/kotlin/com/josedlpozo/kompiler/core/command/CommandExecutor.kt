package com.josedlpozo.kompiler.core.command

import arrow.core.Option

interface CommandExecutor {

    fun execute(command: String): Option<String>
}