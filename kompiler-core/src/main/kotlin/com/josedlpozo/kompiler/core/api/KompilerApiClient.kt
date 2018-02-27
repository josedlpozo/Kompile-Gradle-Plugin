package com.josedlpozo.kompiler.core.api

import com.josedlpozo.kompiler.core.logger.Logger
import com.josedlpozo.kompiler.core.models.Kompile

class KompilerApiClient(override val host: String, private val logger: Logger) : ApiClient {

    private val saveCompileEndPoint = "/api/v1/kompiles"

    override fun saveKompile(kompile: Kompile) {
        val (duration, user, project, alias) = kompile
        val parameters = listOf("duration" to duration, "user" to user, "project" to project, "alias" to alias)
        post(saveCompileEndPoint, parameters) {
            it.fold({ logger.e(it) }, {
                logger.d("Successfully saved Kompile with duration $duration seconds in project $project for user $user")
            })
        }
    }

}