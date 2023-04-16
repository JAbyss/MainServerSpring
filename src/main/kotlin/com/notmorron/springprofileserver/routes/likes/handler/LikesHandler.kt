package com.notmorron.springprofileserver.routes.likes.handler

import com.notmorron.springprofileserver.dbs.neo4j.NeoDB
import com.notmorron.springprofileserver.dbs.neo4j.transactions.likes.getLikes
import com.notmorron.springprofileserver.dbs.neo4j.transactions.profile.getProfiles
import com.notmorron.springprofileserver.dbs.neo4j.transactions.likes.createCouple
import com.notmorron.springprofileserver.dbs.neo4j.transactions.likes.dismissLike
import com.notmorron.springprofileserver.extensions.idUser
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*

@Component
class LikesHandler {

    suspend fun getLikes(req: ServerRequest): ServerResponse {
        val idsPerson = NeoDB.getLikes(req.idUser).getOrNull() ?: return ServerResponse.notFound().buildAndAwait()
        val listProfiles = NeoDB.getProfiles(idsPerson).getOrNull() ?: return ServerResponse.notFound().buildAndAwait()

        return ServerResponse.ok().bodyValue(listProfiles).awaitSingle()
    }

    suspend fun createCouple(req: ServerRequest): ServerResponse {
        val idCouplePerson = req.awaitBodyOrNull<Long>() ?: return ServerResponse.badRequest().buildAndAwait()
        return if (NeoDB.createCouple(req.idUser, idCouplePerson).isSuccess)
            ServerResponse.ok().buildAndAwait()
        else
            ServerResponse.status(500).buildAndAwait()
    }

    suspend fun dismissLike(req: ServerRequest): ServerResponse {
        val idLikedPerson = req.awaitBodyOrNull<Long>() ?: return ServerResponse.badRequest().buildAndAwait()
        return if (NeoDB.dismissLike(req.idUser, idLikedPerson).isSuccess)
            ServerResponse.ok().buildAndAwait()
        else
            ServerResponse.status(500).buildAndAwait()
    }
}