package com.notmorron.springprofileserver.routes.profile

import com.notmorron.springprofileserver.routes.profile.handler.ProfileHandler
import org.springframework.web.reactive.function.server.CoRouterFunctionDsl

fun CoRouterFunctionDsl.profileRoute(profileHandler: ProfileHandler) = run {

    "/profile".nest {
        GET("", profileHandler::getProfile)

        // Чисто временная мера
        "/system".nest {
            GET("getSystemFamilyPlan",profileHandler::getSystemFamilyPlan)
            GET("getSystemAttitudesAlcohol",profileHandler::getSystemAttitudesAlcohol)
            GET("getSystemAttitudesFood",profileHandler::getSystemAttitudesFood)
            GET("getSystemEducation",profileHandler::getSystemEducation)
            GET("getSystemInterest",profileHandler::getSystemInterest)
            GET("getSystemOrientation",profileHandler::getSystemOrientation)
            GET("getSystemPurposeDating",profileHandler::getSystemPurposeDating)
            GET("getSystemAttitudesSport",profileHandler::getSystemAttitudesSport)
            GET("getSystemAttitudesSmoking",profileHandler::getSystemAttitudesSmoking)
        }

        "/settings".nest {
            PUT("/selectGender", profileHandler::selectGender)
            PUT("/selectEducation", profileHandler::selectEducation)
            PUT("/selectInterests", profileHandler::selectInterests)
            PUT("/selectPurposeDating", profileHandler::selectPurposeDating)
            PUT("/selectPets", profileHandler::selectPets)
            PUT("/selectLanguages", profileHandler::selectLanguages)
            PUT("/selectOrientation", profileHandler::selectOrientation)
            PUT("/selectAttitudeAlcohol", profileHandler::selectAttitudeAlcohol)
            PUT("/selectAttitudeFood", profileHandler::selectAttitudeFood)
            PUT("/selectAttitudeSmoking", profileHandler::selectAttitudeSmoking)
            PUT("/selectAttitudeSport", profileHandler::selectAttitudeSport)
            PUT("/selectFamilyPlan", profileHandler::selectFamilyPlan)
            PUT("/selectHeight", profileHandler::selectHeight)
            PUT("/selectDescription", profileHandler::selectDescription)
            PUT("/selectFeedFilterGender", profileHandler::selectFeedFilterGender)

            DELETE("/delete", profileHandler::deleteProfile)
        }
        POST("/block", profileHandler::blockProfile)
    }
}
