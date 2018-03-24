package com.josedlpozo.kompile.core.api

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.josedlpozo.kompile.core.api.extensions.toEither
import com.josedlpozo.kompile.core.models.KompileInfo

interface ApiClient {

    val host: String

    fun saveKompile(kompileInfo: KompileInfo)

    fun post(path: String, parameters: List<Pair<String, Any>>) = host.plus(path).httpPost(parameters).response().third.toEither()

    fun get(path: String, parameters: List<Pair<String, Any>>) = host.plus(path).httpGet(parameters).response().third.toEither()

}