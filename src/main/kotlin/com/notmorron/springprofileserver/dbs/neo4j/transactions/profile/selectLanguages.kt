package com.notmorron.springprofileserver.dbs.neo4j.transactions.profile

import com.notmorron.springprofileserver.dbs.neo4j.NeoDB
import org.neo4j.driver.Query

fun NeoDB.selectLanguages(idUser: Long, languages: List<String>) = runCatching{
    transaction.use {
        it.executeWrite { tc ->

            val stringInterests = languages.joinToString(separator = " ") {
                "Merge (p)-[:$it]->(ll)"
            }

            val query = Query("""
                Match(p:Person{idUser: $idUser})
                optional match(l:Languages)
                where l is null
                merge (ll:Languages)
                with p, ll
                optional match (p)-[r]->(ll)
                delete r
                with p, ll
                $stringInterests
            """.intern())

            tc.run(query)
        }
    }
}

fun main(){
    val a = NeoDB.selectLanguages(121, listOf( "Russian", ))
    println(a)
}