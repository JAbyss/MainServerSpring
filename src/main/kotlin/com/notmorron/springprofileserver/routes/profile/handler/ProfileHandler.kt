package com.notmorron.springprofileserver.routes.profile.handler

import com.notmorron.springprofileserver.dbs.neo4j.NeoDB
import com.notmorron.springprofileserver.dbs.neo4j.transactions.filters.selectFeedFilterGender
import com.notmorron.springprofileserver.dbs.neo4j.transactions.profile.*
import com.notmorron.springprofileserver.extensions.idUser
import com.notmorron.springprofileserver.extensions.neo4jToResult
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBodyOrNull
import org.springframework.web.reactive.function.server.buildAndAwait

@Component
class ProfileHandler {

    suspend fun getProfile(req: ServerRequest): ServerResponse {
        val result = NeoDB.getProfile(idUser = req.idUser)
        return if (result.isSuccess)
            ServerResponse.ok().bodyValue(result.getOrNull()!!).awaitSingle()
        else
            ServerResponse.status(500).buildAndAwait()
    }

    suspend fun blockProfile(req: ServerRequest): ServerResponse {
        val idBlockedPerson = req.awaitBodyOrNull<Long>() ?: return ServerResponse.badRequest().buildAndAwait()
        return if (NeoDB.blockPerson(req.idUser, idBlockedPerson).isSuccess)
            ServerResponse.ok().buildAndAwait()
        else
            ServerResponse.status(500).buildAndAwait()
    }

    suspend fun deleteProfile(req: ServerRequest): ServerResponse {
        return if (NeoDB.deleteProfile(req.idUser).isSuccess)
            ServerResponse.ok().buildAndAwait()
        else
            ServerResponse.status(500).buildAndAwait()
    }

    suspend fun getSystemInterest(req: ServerRequest) = ServerResponse.ok().bodyValue(listInterests).awaitSingle()
    suspend fun getSystemPurposeDating(req: ServerRequest) =
        ServerResponse.ok().bodyValue(purposeDatingList).awaitSingle()

    suspend fun getSystemOrientation(req: ServerRequest) = ServerResponse.ok().bodyValue(listOrientation).awaitSingle()
    suspend fun getSystemAttitudesAlcohol(req: ServerRequest) =
        ServerResponse.ok().bodyValue(attitudesAlcoholList).awaitSingle()

    suspend fun getSystemAttitudesFood(req: ServerRequest) =
        ServerResponse.ok().bodyValue(attitudesFoodList).awaitSingle()

    suspend fun getSystemAttitudesSmoking(req: ServerRequest) =
        ServerResponse.ok().bodyValue(attitudesSmokingList).awaitSingle()

    suspend fun getSystemAttitudesSport(req: ServerRequest) =
        ServerResponse.ok().bodyValue(attitudesSportList).awaitSingle()

    suspend fun getSystemEducation(req: ServerRequest) = ServerResponse.ok().bodyValue(educationList).awaitSingle()
    suspend fun getSystemFamilyPlan(req: ServerRequest) = ServerResponse.ok().bodyValue(familyPlansList).awaitSingle()


    suspend fun selectFeedFilterGender(req: ServerRequest) =
        req.neo4jToResult(f = NeoDB::selectFeedFilterGender)

    suspend fun selectHeight(req: ServerRequest) =
        req.neo4jToResult(f = NeoDB::selectHeight)

    suspend fun selectDescription(req: ServerRequest) =
        req.neo4jToResult(f = NeoDB::selectDescription)

    suspend fun selectPurposeDating(req: ServerRequest) =
        req.neo4jToResult(f = NeoDB::selectPurposeDating)

    suspend fun selectAttitudeAlcohol(req: ServerRequest) =
        req.neo4jToResult(f = NeoDB::selectAttitudeAlcohol)

    suspend fun selectAttitudeSmoking(req: ServerRequest) =
        req.neo4jToResult(f = NeoDB::selectAttitudeSmoking)

    suspend fun selectAttitudeSport(req: ServerRequest) =
        req.neo4jToResult(f = NeoDB::selectAttitudeSport)

    suspend fun selectGender(req: ServerRequest) =
        req.neo4jToResult(f = NeoDB::selectGender)

    suspend fun selectFamilyPlan(req: ServerRequest) =
        req.neo4jToResult(f = NeoDB::selectFamilyPlan)

    suspend fun selectEducation(req: ServerRequest) =
        req.neo4jToResult(f = NeoDB::selectEducation)

    suspend fun selectOrientation(req: ServerRequest) =
        req.neo4jToResult(f = NeoDB::selectOrientation)

    suspend fun selectAttitudeFood(req: ServerRequest) =
        req.neo4jToResult(f = NeoDB::selectAttitudeFood)

    suspend fun selectInterests(req: ServerRequest) =
        req.neo4jToResult(f = NeoDB::selectInterests)

    suspend fun selectLanguages(req: ServerRequest) =
        req.neo4jToResult(f = NeoDB::selectLanguages)

    suspend fun selectPets(req: ServerRequest) =
        req.neo4jToResult(f = NeoDB::selectPets)
}

private val listInterests = listOf(
    "Traveling",
    "Cooking",
    "Reading",
    "Writing",
    "Photography",
    "Music",
    "Dancing",
    "Swimming",
    "Hiking",
    "Camping",
    "Yoga",
    "Pilates",
    "Running",
    "Biking",
    "Surfing",
    "Skiing",
    "Snowboarding",
    "Drawing",
    "Painting",
    "Sculpting",
    "Knitting",
    "Sewing",
    "Gardening",
    "Volunteering",
    "Learning_a_new_language",
    "Playing_an_instrument",
    "Watching_movies",
    "Playing_video_games",
    "Collecting",
    "Woodworking",
    "Fishing",
    "Hunting",
    "Horseback_riding",
    "Water_sports",
    "Martial_arts",
    "Boxing",
    "Football_soccer",
    "Basketball",
    "Tennis",
    "Golf"
)

private val listOrientation = listOf(
    "Heterosexual",
    "Homosexual",
    "Bisexual",
    "Pansexual",
    "Asexual",
    "Demisexual",
    "Sapiosexual",
    "Queer",
    "Androgynosexual",
    "Graysexual",
)

private val purposeDatingList = listOf(
    "Долгосрочный_партнер",
    "Краткосрочный_партнер",
    "Собутыльник",
    "Повеселиться"
)

private val attitudesAlcoholList = listOf(
    "Не_пью",
    "Пью",
    "Каждый_день",
    "Только_по_выходным"
)

private val attitudesFoodList = listOf(
    "Все",
    "Мясо",
    "Траву",
    "Много_чего_не_ем"
)

private val attitudesSmokingList = listOf(
    "Курю",
    "Не_курю",
    "Курю_когда_выпью"
)

private val attitudesSportList = listOf(
    "Часто",
    "Иногда",
    "По выходным",
    "Нельзя"
)

private val educationList = listOf(
    "Высшее",
    "Несколько_высших",
    "Среднее_профессиональное",
    "Школьное"
)

private val familyPlansList = listOf(
    "Хочу_детей",
    "Не_хочу_детей",
    "Есть_дети",
    "Ем_детей"
)