package com.notmorron.springprofileserver.dbs.neo4j.transactions.profile

import com.notmorron.springprofileserver.dbs.neo4j.NeoDB
import org.neo4j.driver.Query

fun NeoDB.deleteProfile(idUser: Long) = runCatching {
    transaction.use {
        it.executeWrite { tc ->

            val query = Query("""
                Match(p:Person{idUser: $idUser})
                detach delete p
            """.intern())

            tc.run(query)
        }
    }
}