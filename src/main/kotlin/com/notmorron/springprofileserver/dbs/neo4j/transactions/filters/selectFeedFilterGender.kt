package com.notmorron.springprofileserver.dbs.neo4j.transactions.filters

import com.notmorron.springprofileserver.dbs.neo4j.NeoDB
import org.neo4j.driver.Query

fun NeoDB.selectFeedFilterGender(idUser: Long, gender: String) = runCatching {
    transaction.use {
        it.executeWrite { tc ->

            val query = Query("""
                Match(p:Person{idUser: $idUser})
                set p.feedFilterGender = '$gender'
            """.intern())

            tc.run(query)
        }
    }
}

fun main() {
    NeoDB.selectFeedFilterGender(121, "Female")
}