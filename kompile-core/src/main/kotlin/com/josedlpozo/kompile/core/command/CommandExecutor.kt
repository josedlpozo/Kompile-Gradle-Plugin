package com.josedlpozo.kompile.core.command

import arrow.core.Option

interface CommandExecutor {

    fun execute(command: String): Option<String>
}