package com.josedlpozo.kompile.core.logger

class Logger(private val verbose: Boolean) {

    fun d(any: Any) {
        if (verbose) println(any.toString())
    }

    fun e(any: Any) = println(any.toString())
}