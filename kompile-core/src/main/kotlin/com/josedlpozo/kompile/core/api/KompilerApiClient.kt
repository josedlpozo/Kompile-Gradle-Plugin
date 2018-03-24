package com.josedlpozo.kompile.core.api

import com.josedlpozo.kompile.core.logger.Logger
import com.josedlpozo.kompile.core.models.KompileInfo

class KompilerApiClient(override val host: String, private val logger: Logger) : ApiClient {

    private val saveCompileEndPoint = "/api/v1/kompiles"

    override fun saveKompile(kompileInfo: KompileInfo) {
        val (duration, user, project, alias) = kompileInfo
        val parameters = listOf("duration" to duration, "user" to user, "project" to project, "alias" to alias)
        post(saveCompileEndPoint, parameters).fold({ logger.e(it) }, {
            logger.d("Successfully saved Kompile with duration $duration seconds in project $project for user $user")
        })
    }

}