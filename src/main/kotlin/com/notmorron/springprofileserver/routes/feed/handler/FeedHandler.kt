package com.notmorron.springprofileserver.routes.feed.handler

import com.notmorron.springprofileserver.dbs.neo4j.NeoDB
import com.notmorron.springprofileserver.dbs.neo4j.transactions.feed.likePerson
import com.notmorron.springprofileserver.dbs.neo4j.transactions.filters.getFeedFilters
import com.notmorron.springprofileserver.dbs.neo4j.transactions.filters.getFeedProfilesByFilter
import com.notmorron.springprofileserver.extensions.idUser
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*

@Component
class FeedHandler {

    suspend fun getFeed(req: ServerRequest): ServerResponse {
        val filters = NeoDB.getFeedFilters(req.idUser)
        filters.onSuccess {
            NeoDB.getFeedProfilesByFilter(it).onSuccess {
                return ServerResponse.ok().bodyValue(it).awaitSingle()
            }.onFailure {
                return ServerResponse.status(500).buildAndAwait()
            }
        }.onFailure {
            return ServerResponse.status(500).buildAndAwait()
        }
        return ServerResponse.status(500).buildAndAwait()
    }

    suspend fun likePerson(req: ServerRequest): ServerResponse {
        val idSecondPerson = req.awaitBodyOrNull<Long>() ?: return ServerResponse.badRequest().buildAndAwait()
        return if (NeoDB.likePerson(req.idUser, idSecondPerson).isSuccess)
            ServerResponse.ok().buildAndAwait()
        else
            ServerResponse.status(500).buildAndAwait()
    }
}