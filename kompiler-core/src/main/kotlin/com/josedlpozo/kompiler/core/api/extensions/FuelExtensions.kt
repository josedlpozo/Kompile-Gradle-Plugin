package com.josedlpozo.kompiler.core.api.extensions

import arrow.core.Either
import arrow.core.Left
import arrow.core.Right
import com.github.kittinunf.result.Result

fun <E : Exception, S : Any> Result<S, E>.toEither(): Either<E, S> = fold({ Right(it) }, { Left(it) })