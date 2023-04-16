package com.notmorron.springprofileserver.dbs.neo4j.transactions.likes

import com.notmorron.springprofileserver.dbs.neo4j.NeoDB
import org.neo4j.driver.Query

fun NeoDB.dismissLike(idUser: Long, idLikedPerson: Long) = runCatching {
    transaction.use {
        it.executeRead { tc ->

            val query = Query("""
                Match (p:Person{idUser: $idUser})
                Match (lp:Person{idUser: $idLikedPerson})
                Match (p)<-[r:LIKE]-(lp)
                delete r
            """.intern())

            tc.run(query)
        }
    }
}