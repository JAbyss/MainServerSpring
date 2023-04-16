package com.notmorron.springprofileserver.dbs.neo4j.transactions.profile

import com.notmorron.springprofileserver.dbs.neo4j.NeoDB
import org.neo4j.driver.Query

fun NeoDB.selectInterests(idUser: Long, listInterests: List<String>) = runCatching {
    transaction.use {

        it.executeWrite { tc ->

            val stringInterests = listInterests.joinToString(separator = " ") {
                "MERGE (p)-[:$it]->(ii)"
            }

            val query = Query("""
                match (p:Person{idUser: 121})
                optional match(i:Interests)
                where i is null
                merge(ii:Interests)
                with p, ii
                optional match (p)-[r]->(ii)
                delete r
                with p, ii
                $stringInterests
            """.intern())

            tc.run(query)
        }
    }
}