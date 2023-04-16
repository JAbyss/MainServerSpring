package com.notmorron.springprofileserver.dbs.neo4j.transactions.likes

import com.notmorron.springprofileserver.dbs.neo4j.NeoDB
import org.neo4j.driver.Query

fun NeoDB.getLikes(idUser: Long) = runCatching {
    transaction.use {
        it.executeRead { tc ->

            val query = Query("""
                Match (p:Person{idUser: $idUser})
                Match (p)<-[:LIKE]-(pp:Person)
                return pp.idUser as idUser 
            """.intern())

            return@executeRead tc.run(query).list().map {
                it.asMap()["idUser"].toString().toLong()
            }
        }
    }
}