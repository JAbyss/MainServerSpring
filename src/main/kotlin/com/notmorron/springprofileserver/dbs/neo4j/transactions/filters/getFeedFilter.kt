package com.notmorron.springprofileserver.dbs.neo4j.transactions.filters

import com.notmorron.springprofileserver.dbs.neo4j.NeoDB
import com.notmorron.springprofileserver.dbs.neo4j.models.FeedFilter
import org.neo4j.driver.Query

fun NeoDB.getFeedFilters(idUser: Long) = runCatching {
    return@runCatching transaction.use {
        it.executeRead { tc ->

            val query = Query("""
                Match(p:Person{idUser: $idUser})
                return p.feedFilterGender as feedFilterGender
            """.intern())

            val map = tc.run(query).single().asMap()
            return@executeRead FeedFilter(
                gender = map["feedFilterGender"].toString()
            )
        }
    }
}