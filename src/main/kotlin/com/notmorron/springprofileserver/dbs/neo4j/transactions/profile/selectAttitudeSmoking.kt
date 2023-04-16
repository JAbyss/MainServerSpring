package com.notmorron.springprofileserver.dbs.neo4j.transactions.profile

import com.notmorron.springprofileserver.dbs.neo4j.NeoDB
import org.neo4j.driver.Query

fun NeoDB.selectAttitudeSmoking(idUser: Long, attitudeSmoking: String) = runCatching {
    transaction.use {
        it.executeWrite { tc ->

            val query = Query("""
                Match(p:Person{idUser: $idUser})
                optional Match(a:AttitudeSmoking)
                where a is null
                Merge (aa:AttitudeSmoking)
                with p, aa
                optional match (p)-[r]->(aa)
                delete r
                with p, aa
                Merge (p)-[:$attitudeSmoking]->(aa)
            """.intern())

            tc.run(query)
        }
    }
}