package com.notmorron.springprofileserver.dbs.neo4j.transactions.profile

import com.notmorron.springprofileserver.dbs.neo4j.NeoDB
import org.neo4j.driver.Query

fun NeoDB.selectPets(idUser: Long, pets: List<String>) = runCatching{
    transaction.use {
        it.executeWrite { tc ->

            val stringInterests = pets.joinToString(separator = " ") {
                "Merge (p)-[:$it]->(pp)"
            }

            val query = Query("""
                Match(p:Person{idUser: $idUser})
                optional match(pets:Pets)
                where pets is null
                merge (pp:Pets)
                with p, pp
                optional match (p)-[r]->(pp)
                delete r
                with p, pp
                $stringInterests
            """.intern())

            tc.run(query)
        }
    }
}

fun main(){
    val a = NeoDB.selectPets(121, listOf("Dogi", "Cati", "Popa"))
    println(a)
}