package com.notmorron.springprofileserver.dbs.neo4j.transactions.couples

import com.notmorron.springprofileserver.dbs.neo4j.NeoDB
import org.neo4j.driver.Query

fun NeoDB.getCouples(idUser: Long) = runCatching {
    transaction.use {
        it.executeRead { tc ->

            val query = Query("""
                Match (p:Person{idUser: $idUser})
                Match (p)-[:COUPLE]->(pp:Person)
                return pp.idUser as idUser 
            """.intern())

            return@executeRead tc.run(query).list().map {
                it.asMap()["idUser"].toString().toLong()
            }
        }
    }
}