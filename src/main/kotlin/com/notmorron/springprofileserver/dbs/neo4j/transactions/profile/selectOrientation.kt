package com.notmorron.springprofileserver.dbs.neo4j.transactions.profile

import com.notmorron.springprofileserver.dbs.neo4j.NeoDB
import org.neo4j.driver.Query

fun NeoDB.selectOrientation(idUser: Long, orientation: String) = runCatching {
    transaction.use {
        it.executeWrite {  tc ->

//            val delQuery = Query(
//                """
//                    Optional Match(p:Person{idUser: $idUser})-[r]->(o:Orientation)
//                    delete r
//                """.intern()
//            )

            val writeQuery = Query(
                """
                    Match(p:Person{idUser: $idUser})
                    optional Match(o:Orientation)
                    where o is null
                    Merge (z:Orientation)
                    with p,z
                    optional Match (p)-[r]->(z)
                    delete r
                    with p,z
                    Merge (p)-[:$orientation]->(z)
                """.intern()
            )

//            tc.run(delQuery)
            tc.run(writeQuery)
        }
    }
}