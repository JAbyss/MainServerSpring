package com.notmorron.springprofileserver.dbs.neo4j.transactions.profile

import com.notmorron.springprofileserver.dbs.neo4j.NeoDB
import org.neo4j.driver.Query

fun NeoDB.getInterests(idUser: Long) = runCatching {
    return@runCatching transaction.use {
        it.executeRead { tc ->

            val query = Query(
                """
                Match(p:Person{idUser: $idUser})-[r]->(:Interests)
                return type(r)
            """.intern()
            )

            val listInterests = try {
                val request = tc.run(query)
                request?.list {
                    it?.fields()?.first()?.value()?.asString()!!
                }!!
            } catch (e: NoSuchElementException) {
                emptyList<String>()
            }



            return@executeRead listInterests
        }
    }
}

fun main() {
    val a = NeoDB.getInterests(121)
    println(a)
}