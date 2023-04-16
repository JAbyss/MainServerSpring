package com.notmorron.springprofileserver.dbs.neo4j.transactions.likes

import com.notmorron.springprofileserver.dbs.neo4j.NeoDB
import org.neo4j.driver.Query

fun NeoDB.createCouple(idUser: Long, idCouplePerson: Long) = runCatching {
    transaction.use {
        it.executeWrite { tc ->

            val query = Query(
                """
                Match (p:Person{idUser: $idUser})
                Match (cp:Person{idUser: $idCouplePerson})
                Merge (p)-[:COUPLE]->(cp)
                Merge (cp)-[:COUPLE]->(p)
                with p, cp
                Match(p)-[r:LIKE]-(cp)
                delete r
            """.intern()
            )

            tc.run(query)
        }
    }
}