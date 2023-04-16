package com.notmorron.springprofileserver.routes.couple.handler

import com.notmorron.springprofileserver.dbs.neo4j.NeoDB
import com.notmorron.springprofileserver.dbs.neo4j.transactions.couples.getCouples
import com.notmorron.springprofileserver.dbs.neo4j.transactions.profile.getProfiles
import com.notmorron.springprofileserver.extensions.idUser
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.buildAndAwait

@Component
class CoupleHandler {

    suspend fun getCouples(req: ServerRequest): ServerResponse {
        val idsCouplesPerson =
            NeoDB.getCouples(req.idUser).getOrNull() ?: return ServerResponse.notFound().buildAndAwait()
        val profiles = NeoDB.getProfiles(idsCouplesPerson).getOrNull() ?: ServerResponse.notFound().buildAndAwait()

        return ServerResponse.ok().bodyValue(profiles).awaitSingle()
    }
}