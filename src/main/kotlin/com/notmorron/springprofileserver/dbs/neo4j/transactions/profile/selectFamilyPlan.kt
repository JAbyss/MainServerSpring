package com.notmorron.springprofileserver.dbs.neo4j.transactions.profile

import com.notmorron.springprofileserver.dbs.neo4j.NeoDB
import org.neo4j.driver.Query

fun NeoDB.selectFamilyPlan(idUser: Long, familyPlan: String) = runCatching {
    transaction.use {
        it.executeWrite { tc ->

            val query = Query("""
                Match(p:Person{idUser: $idUser})
                optional Match(a:FamilyPlan)
                where a is null
                Merge (aa:FamilyPlan)
                with p, aa
                optional match (p)-[r]->(aa)
                delete r
                with p, aa
                Merge (p)-[:$familyPlan]->(aa)
            """.intern())

            tc.run(query)
        }
    }
}