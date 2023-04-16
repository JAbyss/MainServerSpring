package com.notmorron.springprofileserver.dbs.neo4j.transactions.feed

import com.notmorron.springprofileserver.dbs.neo4j.NeoDB
import org.neo4j.driver.Query

fun NeoDB.likePerson(idUser: Long, secondPerson: Long) = runCatching {
    transaction.use {
        it.executeWrite { tc ->

            val query = Query("""
                Merge(:Person{idUser: $idUser})-[:LIKE]->(:Person{idUser: $secondPerson})
            """.intern())

            tc.run(query)
        }
    }
}