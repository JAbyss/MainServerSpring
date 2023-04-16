package com.notmorron.springprofileserver.dbs.neo4j.transactions.profile

import com.fasterxml.jackson.databind.ObjectMapper
import com.notmorron.springprofileserver.dbs.neo4j.NeoDB
import org.neo4j.driver.Query

fun NeoDB.deleteInterests(idUser: Long, listInterests: List<String?>): Result<Any> = runCatching{
    transaction.use {
        it.executeWrite { tc ->

            val jsonString = ObjectMapper().writeValueAsString(listInterests)

            val query = Query("""
                Match(p:Person{idUser: $idUser})-[r]->(:Interests)
                where type(r) in $jsonString
                delete r
            """.intern())

            tc.run(query)
        }
    }
}