package com.notmorron.springprofileserver.dbs.neo4j.transactions.profile

import com.notmorron.springprofileserver.dbs.neo4j.NeoDB
import org.neo4j.driver.Query

fun NeoDB.selectGender(idUser: Long, genderName: String) = runCatching {
    transaction.use {
            it.executeWrite {  tc ->

                val query = Query("""
                    Match(p:Person{idUser: $idUser})
                    optional match(g:Gender)
                    where g is null
                    merge (z:Gender)
                    with p, z
                    optional match (p)-[r]->(z)
                    delete r
                    with p, z
                    merge (p)-[:$genderName]->(z)
                """.intern())

                tc.run(query)
            }
    }
}

fun main(){
    NeoDB.selectGender(121, "Male")
}