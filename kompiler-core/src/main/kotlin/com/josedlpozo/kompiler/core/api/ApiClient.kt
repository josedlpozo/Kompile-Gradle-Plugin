package com.josedlpozo.kompiler.core.api

import arrow.core.Either
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.josedlpozo.kompiler.core.api.extensions.toEither
import com.josedlpozo.kompiler.core.models.Kompile

interface ApiClient {

    val host: String

    fun saveKompile(kompile: Kompile)

    fun post(path: String, parameters: List<Pair<String, Any>>, response: (Either<Exception, ByteArray>) -> Unit) {
        host.plus(path).httpPost(parameters).response { _, _, result ->
            response(result.toEither())
        }
    }

    fun get(path: String, parameters: List<Pair<String, Any>>, response: (Either<Exception, ByteArray>) -> Unit) {
        host.plus(path).httpGet(parameters).response { _, _, result ->
            response(result.toEither())
        }
    }

}