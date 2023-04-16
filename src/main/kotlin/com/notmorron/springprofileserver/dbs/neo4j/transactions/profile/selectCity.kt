package com.notmorron.springprofileserver.dbs.neo4j.transactions.profile

import com.notmorron.springprofileserver.dbs.neo4j.NeoDB
import org.neo4j.driver.Query

fun NeoDB.selectCity(idUser: Long, city: String) = runCatching {
    transaction.use {
        it.executeWrite { tc ->

            val query = Query("""
                Match(p:Person{idUser: $idUser})
                optional Match(a:City)
                where a is null
                Merge (aa:City)
                with p, aa
                optional match (p)-[r]->(aa)
                delete r
                with p, aa
                Merge (p)-[:$city]->(aa)
            """.intern())

            tc.run(query)
        }
    }
}

fun main(){
    val a = NeoDB.selectCity(121, "Уфа")
    println(a)
}