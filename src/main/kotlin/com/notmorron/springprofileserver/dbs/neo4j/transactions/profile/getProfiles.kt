package com.notmorron.springprofileserver.dbs.neo4j.transactions.profile

import com.notmorron.springprofileserver.dbs.neo4j.models.PersonResponse
import com.notmorron.springprofileserver.dbs.neo4j.NeoDB
import org.neo4j.driver.Query

fun NeoDB.getProfiles(idsPerson: List<Long>) = runCatching {
    return@runCatching transaction.use {
        it.executeRead { tc ->
            val query = Query(
                """
                Match(p:Person)
                where p.idUser in $idsPerson
                optional Match(p)-[gender]->(:Gender)
                optional Match (p)-[orientation]->(:Orientation)
                optional Match (p)-[interests]->(:Interests)
                optional Match (p)-[education]->(:Education)
                optional Match (p)-[languages]->(:Languages)
                optional Match (p)-[food]->(:AttitudeFood)
                optional Match (p)-[alcohol]->(:AttitudeAlcohol)
                optional Match (p)-[smoking]->(:AttitudeSmoking)
                optional Match (p)-[sport]->(:AttitudeSport)
                optional Match (p)-[family]->(:FamilyPlan)
                optional Match (p)-[dating]->(:PurposeDating)
                optional Match (p)-[pets]->(:Pets)
                optional Match (p)-[city]->(:City)
                return p.idUser as idUser, p.username as username, p.description as description, p.height as height, type(gender) as gender, type(orientation) as orientation, Collect(distinct type(interests)) as interests, type(education) as education,Collect(distinct type(languages)) as languages, type(food) as food, type(alcohol) as alcohol, type(smoking) as smoking, type(family) as family, type(dating) as purposeDating, Collect(DISTINCT type(pets)) as pets, type(sport) as sport, type(city) as city""".trimIndent()
            )
            return@executeRead tc.run(query).list().map {
                val map = it.asMap()
                PersonResponse(
                    idUser = map["idUser"].toString().toLong(),
                    username = map["username"].toString(),
                    orientation = map["orientation"].toString(),
                    interests = map["interests"] as List<String>,
                    gender = map["gender"].toString(),
                    education = map["education"].toString(),
                    languages = map["languages"] as List<String>,
                    food = map["food"].toString(),
                    alcohol = map["alcohol"].toString(),
                    smoking = map["smoking"].toString(),
                    family = map["family"].toString(),
                    purposeDating = map["purposeDating"].toString(),
                    pets = map["pets"] as List<String>,
                    sport = map["sport"].toString(),
                    city = map["city"].toString(),
                    height = map["height"].toString(),
                    description = map["description"].toString()
                )
            }
        }
    }
}