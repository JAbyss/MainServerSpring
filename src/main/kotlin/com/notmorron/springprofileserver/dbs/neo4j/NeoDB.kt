package com.notmorron.springprofileserver.dbs.neo4j

import org.neo4j.driver.AuthTokens
import org.neo4j.driver.GraphDatabase

object NeoDB {
    val driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "563214789Qq"))
    val transaction
        get() = driver.session()
}