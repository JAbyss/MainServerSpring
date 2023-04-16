package com.notmorron.springprofileserver.dbs.neo4j.transactions.profile

import com.notmorron.springprofileserver.dbs.neo4j.NeoDB
import org.neo4j.driver.Query

fun NeoDB.blockPerson(idUser: Long, idBlockedUser: Long) = runCatching {
    transaction.use {
        it.executeWrite { tc ->

            val query = Query("""
                Match(p:Person{idUser: $idUser})
                Match(bp:Person{idUser: $idBlockedUser})
                Merge(p)-[:BLOCKED]->(bp)
            """.intern())

            tc.run(query)
        }
    }
}